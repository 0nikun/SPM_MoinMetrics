document.addEventListener('submit', async event => {
    const form = event.target;
    const section = form.closest('#massnahmen');
    if (!section) return;
    event.preventDefault();
    if (section.getAttribute('aria-busy') === 'true') return;

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
        section.replaceWith(replacement);
        const message = replacement.querySelector('.measure-status');
        message.textContent = creating ? 'Maßnahme gespeichert.' : 'Maßnahme gelöscht.';
        message.focus({ preventScroll: true });
    } catch (error) {
        status.textContent = 'Die Aktion konnte nicht bestätigt werden. Bitte prüfe vor einem erneuten Versuch den aktuellen Stand durch Neuladen.';
    } finally {
        section.removeAttribute('aria-busy');
        buttons.forEach(button => { button.disabled = false; });
    }
});
