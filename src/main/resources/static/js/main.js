// Espera a que el DOM esté completamente cargado
document.addEventListener('DOMContentLoaded', function () {
    // Listener para el botón "Registrar Paciente" y carga del formulario en el modal
    const btnRegistrar = document.getElementById('btnRegistrarPaciente');
    if (btnRegistrar) {
        btnRegistrar.addEventListener('click', function (e) {
            e.preventDefault();
            fetch('/registrar', { headers: { 'X-Requested-With': 'XMLHttpRequest' } })
                .then(response => response.text())
                .then(html => {
                    const modalBody = document.getElementById('modalRegistrarPacienteBody');
                    if (modalBody) {
                        modalBody.innerHTML = html;
                    }
                });
        });
    }

    // Ejemplo de otro modal (ajusta según tus necesidades)
    const modal = document.getElementById('modal1');
    if (modal) {
        const modalTitle = document.getElementById('modalTitle');
        const modalBody = document.getElementById('modalBody');
        modal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            const title = button.getAttribute('data-title');
            const body = button.getAttribute('data-body');
            if (modalTitle) modalTitle.textContent = title;
            if (modalBody) modalBody.textContent = body;
        });
    }
});