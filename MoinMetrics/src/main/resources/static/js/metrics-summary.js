const summaryTitles = {
    'chart-0': 'Umsatzstärkster Wochentag',
    'chart-2': 'Umsatzstärkste Uhrzeit',
    'chart-1': 'Kundenstärkster Wochentag',
    'chart-3': 'Kundenstärkste Uhrzeit',
    'chart-4': 'Umsatzstärkste Uhrzeit je Tag',
    'chart-item-revenue': 'Top- und Flop-Artikel nach Umsatz',
    'chart-item-margin': 'Top- und Flop-Artikel nach Deckungsbeitrag',
    rules: 'Gemeinsam gekaufte Waren'
};
const summaryEuro = new Intl.NumberFormat('de-DE', { style: 'currency', currency: 'EUR' });
const summaryNumber = new Intl.NumberFormat('de-DE');

function setMetricSummaryStatus(id, message) {
    const body = document.getElementById('summary-' + id);
    if (body) body.textContent = message;
}

function metricExtrema(labels, values, minimum = false) {
    const entries = labels.map((label, index) => [label, values[index]])
        .filter(([, value]) => typeof value === 'number' && Number.isFinite(value));
    if (!entries.length) return null;
    const best = entries.reduce((value, entry) => minimum ? Math.min(value, entry[1]) : Math.max(value, entry[1]), entries[0][1]);
    return { labels: entries.filter(([, value]) => value === best).map(([label]) => label), value: best };
}

function summaryElement(tag, className, text) {
    const element = document.createElement(tag);
    element.className = className;
    if (text !== undefined) element.textContent = text;
    return element;
}

function summaryWinner(result, format) {
    const block = summaryElement('div', 'summary-winner');
    if (!result) {
        block.textContent = 'Noch keine Daten vorhanden.';
        return block;
    }
    block.append(
        summaryElement('strong', 'summary-name', result.labels.join(', ')),
        summaryElement('span', 'summary-value', format(result.value))
    );
    if (result.labels.length > 1) block.append(summaryElement('span', 'summary-tie', 'Gleichstand · Wert je Eintrag'));
    return block;
}

function showMetricSummary(canvas, config) {
    const daily = Boolean(canvas.dataset.originalValues);
    const id = daily ? 'chart-4' : canvas.id;
    const body = document.getElementById('summary-' + id);
    if (!body) return;
    const values = daily ? JSON.parse(canvas.dataset.originalValues) : config.data.datasets[0].data;
    const customers = id === 'chart-1' || id === 'chart-3';
    const format = value => customers ? summaryNumber.format(value) + ' Kunden' : summaryEuro.format(value);
    const maximum = metricExtrema(config.data.labels, values);
    if (daily) {
        if (!body.dataset.loaded) { body.replaceChildren(); body.dataset.loaded = 'true'; }
        const row = summaryElement('div', 'summary-day');
        row.append(summaryElement('h3', 'summary-day-name', config.data.datasets[0].label), summaryWinner(maximum, format));
        body.append(row);
    } else if (id.startsWith('chart-item-')) {
        body.replaceChildren();
        for (const [label, result] of [['Top', maximum], ['Flop', metricExtrema(config.data.labels, values, true)]]) {
            const row = summaryElement('div', 'summary-article summary-article-' + label.toLowerCase());
            row.append(summaryElement('span', 'summary-badge', label), summaryWinner(result, format));
            body.append(row);
        }
    } else {
        body.replaceChildren(summaryWinner(maximum, format));
    }
}

function showRulesSummary(rules) {
    const body = document.getElementById('summary-rules');
    if (!rules.length) { body.textContent = 'Keine gemeinsamen Kaufregeln gefunden.'; return; }
    const top = [...rules].sort((a, b) => b.lift - a.lift || b.confidence - a.confidence).slice(0, 5);
    const names = value => value.replace(/^\[|\]$/g, '').replace(/=1(?=,|$)/g, '').split(', ').join(' + ');
    const note = summaryElement('p', 'summary-rule-note');
    note.textContent = 'Bis zu fünf Kaufregeln mit dem höchsten Lift. Alle Regeln und Kennzahlen im Advanced-Modus.';
    const list = summaryElement('ol', 'summary-rules');
    for (const rule of top) {
        const row = summaryElement('li', 'summary-rule');
        const pair = summaryElement('div', 'summary-rule-pair');
        pair.append(summaryElement('strong', '', names(rule.premise)),
            summaryElement('span', 'summary-rule-arrow', '→'), summaryElement('strong', '', names(rule.conclusion)));
        const confidence = summaryElement('div', 'summary-rule-confidence');
        confidence.append(summaryElement('strong', '', percentageFormat.format(rule.confidence * 100) + ' %'),
            summaryElement('span', '', 'der Einkäufe mit der Ausgangsware enthalten auch die Folgeware'));
        row.append(pair, confidence);
        list.append(row);
    }
    body.replaceChildren(note, list);
}

document.addEventListener('DOMContentLoaded', () => {
    const summary = document.getElementById('metrics-summary');
    const intro = summaryElement('header', 'summary-intro');
    intro.append(summaryElement('h2', '', 'Deine Auswertung auf einen Blick'),
        summaryElement('p', '', 'Die wichtigsten Ergebnisse deiner importierten Daten. Details findest du im Advanced-Modus.'));
    summary.append(intro);
    for (const [id, title] of Object.entries(summaryTitles)) {
        const card = document.createElement('article');
        const category = id === 'rules' ? 'rules' : id === 'chart-4' ? 'days' : id.startsWith('chart-item-') ? 'articles' : 'highlight';
        card.className = 'chart-card summary-card summary-card-' + category;
        if (id === 'chart-1' || id === 'chart-3') card.classList.add('summary-card-customers');
        const heading = document.createElement('h2');
        heading.textContent = title;
        const body = document.createElement('div');
        body.id = 'summary-' + id;
        body.className = 'summary-result';
        body.setAttribute('role', 'status');
        body.textContent = 'Daten werden geladen …';
        card.append(heading, body);
        summary.append(card);
    }
    const toggle = document.getElementById('advanced-mode');
    toggle.checked = false;
    const update = () => {
        const advanced = toggle.checked;
        summary.hidden = advanced;
        document.getElementById('metrics-charts').hidden = !advanced;
        document.querySelector('.metrics-display-control').hidden = !advanced;
        document.getElementById('renderZeit').hidden = !advanced;
        document.getElementById('advanced-state').textContent = advanced ? 'An' : 'Aus';
        if (advanced) requestAnimationFrame(() => {
            Object.values(Chart.instances).forEach(chart => { chart.resize(); });
        });
    };
    toggle.addEventListener('change', update);
    update();
});
