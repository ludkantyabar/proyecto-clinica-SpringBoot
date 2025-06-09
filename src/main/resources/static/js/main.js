// Selecciona el modal y sus elementos
const modal = document.getElementById('modal1');
const modalTitle = document.getElementById('modalTitle');
const modalBody = document.getElementById('modalBody');

// Escucha el evento 'show.bs.modal' para actualizar el contenido
modal.addEventListener('show.bs.modal', function (event) {
    // Botón que activó el modal
    const button = event.relatedTarget;

    // Obtén los datos del botón
    const title = button.getAttribute('data-title');
    const body = button.getAttribute('data-body');

    // Actualiza el contenido del modal
    modalTitle.textContent = title;
    modalBody.textContent = body;
});