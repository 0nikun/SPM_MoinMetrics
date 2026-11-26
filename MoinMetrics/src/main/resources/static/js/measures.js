function updateEditButton(form) {
    const { name, beschreibung } = form.elements;
    const changed = name.value !== name.defaultValue || beschreibung.value !== beschreibung.defaultValue;
    form.querySelector('button[type="submit"]').disabled = !changed || Boolean(form.closest('[aria-busy="true"]'));
}

function syncEditForms(container) {
    container.querySelectorAll('.measure-editor form').forEach(updateEditButton);
}

syncEditForms(document);

document.addEventListener('input', event => {
    const form = event.target.closest('.measure-editor form');
    if (form) updateEditButton(form);
});

document.addEventListener('reset', event => {
    const form = event.target;
    if (!form.matches('.measure-editor form')) return;
    form.querySelector('button[type="submit"]').disabled = true;
    const details = form.closest('tbody').querySelector('.measure-edit');
    details.open = false;
    details.querySelector('summary').focus();
});

document.addEventListener('submit', async event => {
    const form = event.target;
    const section = form.closest('#massnahmen');
    if (!section) return;
    event.preventDefault();
    if (section.getAttribute('aria-busy') === 'true') return;
    if (form.matches('.measure-editor form') && form.querySelector('button[type="submit"]').disabled) return;

    const creating = form.classList.contains('measure-form');
    const body = new URLSearchParams(new FormData(form));
    const status = section.querySelector('.measure-status');
    const buttons = [...section.querySelectorAll('button')];
    section.setAttribute('aria-busy', 'true');
    buttons.forEach(button => { button.disabled = true; });
    status.textContent = creating ? 'Maßnahme wird gespeichert …' : 'Maßnahme wird gelöscht …';
    try {
        const response = await fetch(form.action, {
            method: 'POST',
            headers: { 'X-Requested-With': 'XMLHttpRequest' },
            body
        });
        if (!response.ok || response.redirected) throw new Error('Unexpected response');
        const documentFragment = new DOMParser().parseFromString(await response.text(), 'text/html');
        const replacement = documentFragment.getElementById('massnahmen');
        if (!replacement) throw new Error('Missing measures fragment');
        syncEditForms(replacement);
        section.replaceWith(replacement);
        const message = replacement.querySelector('.measure-status');
        message.textContent = creating ? 'Maßnahme gespeichert.' : 'Maßnahme gelöscht.';
        message.focus({ preventScroll: true });
    } catch (error) {
        status.textContent = 'Die Aktion konnte nicht bestätigt werden. Bitte prüfe vor einem erneuten Versuch den aktuellen Stand durch Neuladen.';
    } finally {
        section.removeAttribute('aria-busy');
        buttons.forEach(button => { button.disabled = false; });
        syncEditForms(section);
    }
});
