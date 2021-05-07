$("#add-student-submit").on("click", () => {
    if (validInput() === true) {
        $.ajax({
            method: "POST",
            url: "/addStudent",
            data: JSON.stringify(studentObject()),
            contentType: "application/json",
            success: response => {
                alert(`SUCCESS: ${response}`);
                window.location.href = "/";
            },
            error: err => {
                let errorObj = err.responseJSON;
                alert(`ERROR: "${errorObj.message}" \nSTATUS: ${errorObj.status}`);
            }
        });
    } else {
        alert("Invalid input");
    }
});

const studentObject = () => {
    return {
        name: $("#name").val(),
        surname: $("#surname").val(),
        email: $("#email").val()
    };
};

const validInput = () => {
    return $("#name").val() && $("#surname").val() &&  $("#email").val()!=="";
};