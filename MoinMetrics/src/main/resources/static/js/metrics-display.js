const metricsDisplays = [];
const percentageFormat = new Intl.NumberFormat('de-DE', { maximumFractionDigits: 1 });

function createMetricsChart(canvas, config) {
    const customerChart = canvas.id === 'chart-1' || canvas.id === 'chart-3';
    const marginChart = canvas.id === 'chart-item-margin';
    const color = marginChart ? '#8770a3' : customerChart ? '#5283a0' : '#37836d';
    const card = canvas.closest('.chart-card');
    card.dataset.metric = marginChart ? 'margin' : customerChart ? 'customers' : 'revenue';
    for (const dataset of config.data.datasets) {
        dataset.backgroundColor = color;
        dataset.hoverBackgroundColor = marginChart ? '#6b5488' : customerChart ? '#346582' : '#216650';
        dataset.borderRadius = 5;
        dataset.maxBarThickness = 44;
    }
    config.options.layout = { padding: { top: 12, right: 12 } };
    for (const [name, scale] of Object.entries(config.options.scales)) {
        const valueAxis = name === (config.options.indexAxis === 'y' ? 'x' : 'y');
        scale.border = { display: false };
        scale.grid = { color: '#eaf0ec', display: valueAxis, drawTicks: false };
        scale.ticks = { ...scale.ticks, color: '#60756b', padding: scale.ticks?.padding ?? 10,
            font: { family: 'system-ui, sans-serif', size: 12, ...scale.ticks?.font } };
    }
    const tooltipStyle = {
        backgroundColor: '#193e33', titleColor: '#fff', bodyColor: '#eff7f2',
        padding: 12, cornerRadius: 8, displayColors: false,
        titleFont: { family: 'system-ui, sans-serif', size: 13 },
        bodyFont: { family: 'system-ui, sans-serif', size: 13 }
    };
    config.options.plugins.tooltip = { ...tooltipStyle, ...config.options.plugins.tooltip };
    showMetricSummary(canvas, config);
    if (canvas.id === 'chart-item-margin') return new Chart(canvas, config);

    const daily = Boolean(canvas.dataset.originalValues);
    const raw = daily ? JSON.parse(canvas.dataset.originalValues) : [...config.data.datasets[0].data];
    const originalData = [...config.data.datasets[0].data];
    const total = raw.reduce((sum, value) => sum + value, 0);
    const eligible = total > 0 && raw.every(value => Number.isFinite(value) && value >= 0);
    const axis = config.options.indexAxis === 'y' ? 'x' : 'y';
    const originalScale = config.options.scales[axis];
    const originalLabel = config.data.datasets[0].label;
    const originalBase = config.data.datasets[0].base;
    const originalTooltip = config.options.plugins.tooltip;
    const customers = canvas.id === 'chart-1' || canvas.id === 'chart-3';
    const basis = daily ? 'Tagesumsatz' : customers ? 'Anzahl der Kunden im Datensatz' : 'Gesamtumsatz der dargestellten Daten';
    const note = document.createElement('p');
    note.className = 'chart-scale-note';
    note.setAttribute('aria-live', 'polite');
    canvas.parentElement.parentElement.append(note);

    function configure(data, options, requested) {
        const relative = requested && eligible;
        data.datasets[0].data = relative ? raw.map(value => value / total * 100) : [...originalData];
        data.datasets[0].label = relative ? 'Anteil in %' : originalLabel;
        if (relative) data.datasets[0].base = 0;
        else if (originalBase !== undefined) data.datasets[0].base = originalBase;
        else delete data.datasets[0].base;
        options.scales[axis] = relative ? {
            type: 'linear', min: 0,
            ...(daily ? { max: Number(canvas.dataset.relativeMax) } : {}),
            border: originalScale.border, grid: originalScale.grid,
            title: { display: true, text: 'Anteil in %' },
            ticks: { ...originalScale.ticks, precision: undefined, stepSize: undefined, callback: value => percentageFormat.format(value) + ' %' }
        } : { ...originalScale };
        options.plugins.tooltip = relative ? {
            ...tooltipStyle,
            callbacks: { label: context => 'Anteil: ' + percentageFormat.format(context.parsed[axis]) + ' %' }
        } : (originalTooltip || {});
        note.textContent = relative ? '100 % = ' + basis + '.' :
            requested ? 'Absolute Werte: Prozentanteile sind bei negativen Werten oder einer Summe von null nicht sinnvoll.' : '';
        note.hidden = !note.textContent;
        if (daily) {
            canvas.closest('.day-chart').querySelectorAll('.chart-scale-note').forEach(item => {
                if (item !== note) item.hidden = relative;
            });
        }
    }
    const requested = document.getElementById('relative-values').checked;
    configure(config.data, config.options, requested);
    const chart = new Chart(canvas, config);
    metricsDisplays.push(value => {
        configure(chart.data, chart.options, value);
        chart.update('none');
    });
    return chart;
}

document.addEventListener('DOMContentLoaded', () => {
    const control = document.getElementById('relative-values');
    document.querySelector('.metrics-display-control').addEventListener('change', () => {
        metricsDisplays.forEach(update => update(control.checked));
    });
});
