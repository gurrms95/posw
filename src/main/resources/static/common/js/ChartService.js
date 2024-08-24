// 유틸리티 함수: CSS 파일 로드
function loadCSS(url) {
    return new Promise((resolve, reject) => {
        const link = document.createElement('link');
        link.rel = 'stylesheet';
        link.href = url;
        link.onload = () => resolve();
        link.onerror = () => reject(new Error(`Failed to load CSS: ${url}`));
        document.head.appendChild(link);
    });
}

// 유틸리티 함수: JS 파일 로드
function loadJS(url) {
    return new Promise((resolve, reject) => {
        const script = document.createElement('script');
        script.src = url;
        script.onload = () => resolve();
        script.onerror = () => reject(new Error(`Failed to load JS: ${url}`));
        document.head.appendChild(script);
    });
}

/*
 * 공통 - Chart 서비스 클래스
 */
class ChartService {
    
    constructor() {
        this.chartInitialized = this.initChart();
    }

    async initChart() {
        try {
            await loadCSS('https://cdn.jsdelivr.net/npm/chart.js/dist/Chart.min.css');
            await loadJS('https://cdn.jsdelivr.net/npm/chart.js');
            await loadJS('https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels');
            await loadJS('/js/envir_score/envir_score_doughnutchart.js');
            await loadJS('/js/envir_score/envir_score_linechart.js');
            console.log('ChartJS loaded successfully');
        } catch (error) {
            console.error('ChartJS initialization failed:', error);
        }
    }

    async renderChart(elementId, options) {
        await this.chartInitialized;
        const canvasElement = document.getElementById(elementId);
        if (canvasElement) {
            const ctx = canvasElement.getContext('2d');
            new Chart(ctx, options);
        } else {
            console.error(`Element with id "${elementId}" not found.`);
        }
    }

    async renderDoughnutChart(elementId, options) {
        await this.renderChart(elementId, options);
    }

    async renderLineChart(elementId, options) {
        await this.renderChart(elementId, options);
    }

    async renderBarChart(elementId, options) {
        await this.renderChart(elementId, options);
    }

    async renderPieChart(elementId, options) {
        await this.renderChart(elementId, options);
    }

    async renderRadarChart(elementId, options) {
        await this.renderChart(elementId, options);
    }

    async renderPolarAreaChart(elementId, options) {
        await this.renderChart(elementId, options);
    }

    async renderBubbleChart(elementId, options) {
        await this.renderChart(elementId, options);
    }

    async renderScatterChart(elementId, options) {
        await this.renderChart(elementId, options);
    }
}

export default ChartService;
