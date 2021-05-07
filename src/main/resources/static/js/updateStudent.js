$(document).ready(() => {
    const id = new URL(document.URL).searchParams.get("uid");
    $.ajax({
        url: `/getStudent/${id}`,
        method: "GET",
        success: students => {
            fillInputs(students);
        },
        error: err => {
            const errorObj = err.responseJSON;
            alert(`ERROR: "${errorObj.message}" \nSTATUS: ${errorObj.status}`);
        }
    })
});

function fillInputs(data) {
    $("#name-update").val(data.name);
    $("#surname-update").val(data.surname);
    $("#email-update").val(data.email);
}

$("#update-student-submit").on("click", () => {
    const id = new URL(document.URL).searchParams.get("uid");
    $.ajax({
        url: `/updateStudent/${id}`,
        method: "PUT",
        data: JSON.stringify(newUserObj()),
        contentType: "application/json",
        success: response => {
            alert(`SUCCESS: ${response}`);
            window.location.href = "/";
        },
        error: err => {
            const errorObj = err.responseJSON;
            alert(`ERROR: "${errorObj.message}" \nSTATUS: ${errorObj.status}`);
        }
    });
});

const newUserObj = () => {
    return {
        name: $("#name-update").val(),
        surname: $("#surname-update").val(),
        email: $("#email-update").val()
    };
};