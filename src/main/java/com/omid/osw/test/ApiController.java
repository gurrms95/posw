package com.omid.osw.test;

import com.omid.osw.common.utils.ApiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;


@Controller
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private ApiUtils apiUtils;

    @GetMapping("/api_test")
    public String apiTest() {
        return "/api";
    }

    @GetMapping("/wmcb_api")
    @ResponseBody
    public ResponseEntity<String> getWasteMedicineCollectionBoxApi() {
        return apiUtils.getWasteMedicineCollectionBoxApi();
    }

    @GetMapping("/pharmacy_api")
    @ResponseBody
    public ResponseEntity<String> getDrugStoreApi() {
        return apiUtils.getPharmacyApi();
    }
    @GetMapping("/medi_api")
    @ResponseBody
    public ResponseEntity<String> getMediApi() throws Exception {
        return apiUtils.getMediApi("타이레놀");
    }


    @GetMapping("/gcv")
    @ResponseBody
    public void getGCVApi() throws Exception {
        String imagePath = "lib/google_cloud_vision/image.jpg";  // 이미지 파일 경로(테스트용)
        ClassPathResource imageResource = new ClassPathResource(imagePath);
        String imageAbsolutePath = imageResource.getFile().getAbsolutePath();
        String userInput = "약품명,투약량,횟수,일수 이렇게 4개를 순서대로 객체배열형식으로 뽑아주고 부가 설명은 하지 말아줘. 예를들면 [{약품명:펠루비정(펠루비프로펜), 투약량:1, 횟수:3, 일수:3},{약품명:타세놀이알서방정, 투약량:1, 횟수:3, 일수:3}...]";

//        apiUtils.chatGPTResponse(imageAbsolutePath,userInput);

        String text = """
            복약안내 1일 3회 3일분
            매식
            매
            전·간후
            분
            약제비 계산서·영수증(별지 제11호 서식)
            시간마다
            ml씩
            m
            9420240721-0419
            환자성명고용수
            고용수
            음성복약지도
            (남/만 33세)
            처방전교부번호20240721-00086
            처방전 발행기관한 사랑의원
            조제일 2024-07-21 투약일수
            조제약사 복수진
            조제일자 2024-07-21
            약제비총액(①+2+3)
            14.710
            본인부담금①
            4.400
            보험자부담금 ②
            10.310
            약품명
            복약안내
            투약량 횟수 일수
            펠루비정(펠루비프로펜)_(3
            열을 내리고 염증을 가라앉히며 통증을 해소하는 1
            약
            3 3
            비급여(전액본인) ③
            카드
            0
            0
            담황색 정제
            (차광실온보관)
            타세놀이알서방정
            흰색 서방정
            (실온보관)
            에페릴엠SR서방정(에페리손염
            흰색 서방정
            (실온보관)
            스토엠정(애엽95%에탄올연조
            녹색 정제
            (실온보관)
            펠라움에스시럽_(9mL)
            갈색 시럽제
            (실온보관)
            이 약의 투여기간 동안에는 가능한 금주하세요.
            간이나 신장질환 환자의 경우 전문가에게
            총수납금액 현금영수증
            C
            (①+③) 현금
            4,400
            합계
            4,400
            체온조절중추에 작용하여 열을 내리고, 통증을
            해소하는 약
            1
            3 3
            현금영수증 신분확인번호
            전문가와 상의없이 다른 소염진통제와 병용하지
            마세요.
            ) 현금승인번호
            근육의 경직성을 풀어주고, 근경련을 감소시키는
            약
            1
            2 3
            사업자등록번호 314-26-81858
            사업장소재지 대전시 서구 문예로 19
            씹거나 부수지 말고 물과 함께 그대로
            C
            복용하세요.
            위점막을 보호하고, 손상된 점막조직의 재생을
            촉진하는 약
            상성발
            호프라자약국
            1
            3
            3
            습기에 약하므로 습기가 적고 서늘한곳에 보관
            92024-07-21
            ※이 계산서·영수증은 「소득세법」에 의한 의료비 또는 조제한법에 의한
            증(현금영수증 승인번호가 기재된 경우) 공제신청에 사용할 수 있습니다. 다만
            방용으로 발급된 현금영수증(지출증빙), 온 공제신청에 사용할 수 없습니다
            ※이 계산서·영수증에 대한 세부내역을 요구할 수 있습니다.
            ※전액본인부담금이란 국민건강보험법 시행규칙 별표 5의 규정에 의한 요양급
            본인전액부담항목 비용을 말합니다.
            명복수진
            1511
            행
            항바이러스, 항균, 분비촉진작용으로 기관지염에 1
            사용
            알림 : 현금영수증 문의 126
            인터넷홈페이지: http://현금영수증.kr
            3
            3
            """;

//        Pattern pattern = Pattern.compile("[가-힣]+정|[가-힣]+서방정|[가-힣]+시럽|[가-힣]+캡슐");
//        Pattern pattern = Pattern.compile("[가-힣]+(정|서방정|시럽)(?=\\(|_|\\s)");
        Pattern pattern = Pattern.compile("([가-힣]+(?:정|서방정|시럽))\\b(?!\\s*[가-힣])");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            System.out.println(matcher.group());
        }


//        try {
            // Google Cloud Vision API를 사용해 이미지에서 텍스트 추출
//            String ocrResult = apiUtils.gcvApi(imagePath);

            // OCR 결과에서 약품명 추출
//            List<String> medicineNames = extractMedicineNames(ocrResult);
//
//            // API 응답을 담을 리스트
//            List<HashMap<String, Object>> medicineInfoList = new ArrayList<>();
//
//            // 약품명에 대해 API 호출 및 응답 매핑
//            for (String medicineName : medicineNames) {
//                try {
//                    ResponseEntity<String> apiResponse = apiUtils.getMediApi(medicineName);
//                    String responseBody = apiResponse.getBody();
//
//                    // JSON 응답을 HashMap 객체로 변환
//                    Type listType = new TypeToken<List<HashMap<String, Object>>>(){}.getType();
//                    List<HashMap<String, Object>> medicines = new Gson().fromJson(responseBody, listType);
//
//                    // 추출된 객체 리스트를 전체 리스트에 추가
//                    if (medicines != null) {
//                        medicineInfoList.addAll(medicines);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            // 최종 리스트 출력 또는 반환
//            // 여기서는 단순히 출력만 하지만, 필요한 경우 반환할 수 있습니다.
//            System.out.println(medicineInfoList);
//            medicineInfoList.forEach(medicine -> System.out.println(medicine));
//
//            // 성공 메시지 반환
//            //return ResponseEntity.ok("약품명 추출 및 API 호출이 완료되었습니다.");
//        } catch (IOException e) {
//            e.printStackTrace();
//            //return ResponseEntity.status(500).body("오류가 발생했습니다: " + e.getMessage());
//        }
    }


    // OCR 결과에서 약품명을 추출하는 메서드
    private List<String> extractMedicineNames(String ocrResult) {
        List<String> medicineNames = new ArrayList<>();

        // OCR 결과를 줄 단위로 분리
        String[] lines = ocrResult.split("\n");

        // 약품명 필터링 조건: 약품의 일반적인 형태를 포함한 키워드
        String[] keywords = {
                "정", "서방정", "시럽", "캡슐", "연질캡슐", "정제", "액", "산", "과립", "흡입액",
                "분무액", "연고", "로션", "크림", "겔", "파우더", "주사", "액상", "리퀴드", "시럽제",
                "엑스", "용액", "용해액", "수용액", "엠플", "튜브", "파스", "패취", "발포정",
                "흡입제", "스프레이", "로션제", "크림제", "시럽제", "흡입액", "이알정", "서방캡슐",
                "액제", "리퀴드", "주사제", "액상제", "파우더제", "파우더형", "파우더타입"
        };

        for (String line : lines) {
            for (String keyword : keywords) {
                if (line.contains(keyword)) {
                    // 키워드가 포함된 경우, 약품명과 형태를 분리하여 약품명만 추출
                    int index = line.lastIndexOf(keyword);

                    // 키워드 이후의 특수 문자나 숫자 제거
                    String cleanLine = line.substring(0, index + keyword.length()).trim();

                    // 특수 문자를 제거
                    cleanLine = cleanLine.replaceAll("[^\\p{L}\\p{N}\\s]", "").trim();

                    // 약품명을 리스트에 추가
                    medicineNames.add(cleanLine);
                    break;
                }
            }
        }

        return medicineNames;
    }
}
