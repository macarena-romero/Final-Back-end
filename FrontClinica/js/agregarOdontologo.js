const form = document.getElementById("agregarForm");
const apiURL = "http://localhost:8080";

form.addEventListener("submit", function (event) {
  event.preventDefault();

  const apellido = document.getElementById("apellido").value;
  const nombre = document.getElementById("nombre").value;
  const matricula = document.getElementById("matricula").value;


  // llamando al endpoint de agregar
  const datosFormulario = {
    nombre,
    apellido,
    matricula,

  };

  fetch(`${apiURL}/odontologo/guardar`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(datosFormulario),
  })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      alert("Odontologo agregado con éxito");
      form.reset(); // Resetear el formulario
      location.reload(); // Reload the page to reflect changes
    })
    .catch((error) => {
      console.error("Error agregando odontologo:", error);
    });
});
