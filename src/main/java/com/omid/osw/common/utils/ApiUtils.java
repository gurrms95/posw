package com.omid.osw.common.utils;

import com.google.gson.*;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import java.io.IOException;
import java.util.*;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageAnnotatorSettings;
import com.google.protobuf.ByteString;

@Component
public class ApiUtils {
    @Value("${common.api.key}")
    private String apiKey;
    @Value("${waste-medicine-collection-box.api.url}")
    private String wmcb_api_url;
    @Value("${pharmacy.api.url}")
    private String pharmacy_api_url;
    @Value("${medi.api.url}")
    private String medi_api_url;

    @Value("${openai.api.key}")
    private String api_key;

    @Value("${openai.api.url}")
    private String api_url;


    public ResponseEntity<String> getWasteMedicineCollectionBoxApi() {
        return fetchApiResponse("wmcb", null, 1, 300);
    }

    public ResponseEntity<String> getPharmacyApi() {
        return fetchApiResponse("pharmacy", null, 1, 200);
    }

    public ResponseEntity<String> getMediApi(String itemName) throws Exception {
        return fetchApiResponse("medi", itemName, 0, 0);
    }

    private ResponseEntity<String> fetchApiResponse(String apiType, String itemName, int page, int perPage) {
        StringBuilder requestUrl = new StringBuilder();

        try {
            switch (apiType) {
                case "wmcb":
                    requestUrl.append(wmcb_api_url)
                            .append("?serviceKey=").append(apiKey)
                            .append("&page=").append(page)
                            .append("&perPage=").append(perPage);
                    break;
                case "pharmacy":
                    requestUrl.append(pharmacy_api_url)
                            .append("?serviceKey=").append(apiKey)
                            .append("&page=").append(page)
                            .append("&perPage=").append(perPage);
                    break;
                case "medi":
                    requestUrl.append(medi_api_url)
                            .append("&itemName=").append(URLEncoder.encode(itemName, "UTF-8"))
                            .append("&type=").append(URLEncoder.encode("json", "UTF-8"));
                    break;
                default:
                    return ResponseEntity.status(400).body("유효하지 않은 API 입니다.");
            }

            URL url = new URL(requestUrl.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            int responseCode = conn.getResponseCode();
            StringBuilder response = new StringBuilder();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            } else {
                return ResponseEntity.status(responseCode).body("GET 요청이 실패하였습니다.");
            }

            JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
            JsonArray dataArray;

            if ("medi".equals(apiType)) {
                JsonObject body = jsonObject.getAsJsonObject("body");
                if (!body.has("items")) {
                    return ResponseEntity.ok("");
                }
                dataArray = body.getAsJsonArray("items");
            } else {
                dataArray = jsonObject.getAsJsonArray("data");
            }

            System.out.println(dataArray.toString()); //데이터 테스트용으로 현재 쓰는 중
            return ResponseEntity.ok(dataArray.toString());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Exception: " + e.getMessage());
        }
    }

    public String gcvApi(String imagePath) throws IOException {
        // Google Cloud Vision API 사용을 위해 JSON 파일로부터 인증 설정
        ClassPathResource jsonResource = new ClassPathResource("lib/google_cloud_vision/oswocr-dfc647e65d45.json");
        GoogleCredentials credentials;

        try (InputStream credentialsStream = jsonResource.getInputStream()) {
            credentials = GoogleCredentials.fromStream(credentialsStream)
                    .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
        }

        // ImageAnnotatorClient를 생성하면서 인증 설정을 적용
        ImageAnnotatorSettings settings = ImageAnnotatorSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build();

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create(settings)) {
            // 이미지 파일을 resources 폴더에서 로드
            ClassPathResource imageResource = new ClassPathResource(imagePath);
            ByteString imgBytes;

            try (InputStream in = imageResource.getInputStream()) {
                imgBytes = ByteString.readFrom(in);
            }

            Image img = Image.newBuilder().setContent(imgBytes).build();
            Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
            AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                    .addFeatures(feat)
                    .setImage(img)
                    .build();

            List<AnnotateImageRequest> requests = new ArrayList<>();
            requests.add(request);

            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            StringBuilder stringBuilder = new StringBuilder();

            for (AnnotateImageResponse res : response.getResponsesList()) {
                if (res.hasError()) {
                    System.err.printf("Error: %s\n", res.getError().getMessage());
                    return "Error detected: " + res.getError().getMessage();
                }
                stringBuilder.append(res.getFullTextAnnotation().getText());
            }

            return stringBuilder.toString();
        }
    }

    public String chatGPTResponse(String imagePath, String userInput) throws Exception {
        try {
            URL url = new URL(api_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + api_key);
            connection.setDoOutput(true);

            String jsonInputString = requestJson(imagePath, userInput);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int code = connection.getResponseCode();
            System.out.println(code);

            if (code == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                StringBuilder response = new StringBuilder();
                String responseLine = "";

                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
//                System.out.println("response.toString(): " + response.toString());
//                return response.toString();
                System.out.println("답변: " + parseResponse(response.toString()));

                return parseResponse(response.toString());
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "utf-8"));
                StringBuilder errorResponse = new StringBuilder();
                String errorLine = "";

                while ((errorLine = br.readLine()) != null) {
                    errorResponse.append(errorLine.trim());
                }
                System.out.println("Error Response: " + errorResponse.toString());
                throw new IOException("Error: " + errorResponse.toString());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new IOException("Error: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

    }
    private static String requestJson(String imagePath, String userInput) throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("model", "gpt-3.5-turbo");

        JsonArray messages = new JsonArray();

        JsonObject userMessage = new JsonObject();
        userMessage.addProperty("role", "user");

        JsonArray content = new JsonArray();

        JsonObject textContent = new JsonObject();
        textContent.addProperty("type", "text");
        textContent.addProperty("text", userInput);
        content.add(textContent);

        JsonObject imageContent = new JsonObject();
        imageContent.addProperty("type", "image_url");

        String base64Image = encodeImageToBase64(imagePath);
        String mimeType = mimeType(imagePath);

        JsonObject imageUrl = new JsonObject();
        imageUrl.addProperty("url", "data:" + mimeType + ";base64," + base64Image);
        imageContent.add("image_url", imageUrl);

        content.add(imageContent);

        userMessage.add("content", content);
        messages.add(userMessage);

        json.add("messages", messages);
        json.addProperty("max_tokens", 300);

        return new Gson().toJson(json);
    }
    private static String encodeImageToBase64(String imagePath) throws IOException {
        try {
            File file = new File(imagePath);
            FileInputStream imageInFile = new FileInputStream(file);
            byte[] imageData = new byte[(int) file.length()];
            imageInFile.read(imageData);
            imageInFile.close();
            return Base64.getEncoder().encodeToString(imageData);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static String mimeType(String imagePath) {
        String ext = "";

        int i = imagePath.lastIndexOf('.');
        if (i > 0) {
            ext = imagePath.substring(i + 1);
        }

        switch (ext.toLowerCase()) {
            case "png":
                return "image/png";
            case "jpeg":
            case "jpg":
                return "image/jpeg";
            default:
                return "application/octet-stream";
        }
    }

    private static String parseResponse(String jsonResponse) {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(jsonResponse);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray choices = jsonObject.getAsJsonArray("choices");
        if (choices != null && choices.size() > 0) {
            JsonObject choice = choices.get(0).getAsJsonObject();
            JsonObject message = choice.getAsJsonObject("message");
            if (message != null) {
                return message.get("content").getAsString();
            }
        }
        return "답변 내용을 찾지 못하였습니다.";
    }


}
