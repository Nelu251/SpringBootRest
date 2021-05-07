$(document).ready(() => {
    getAllStudents();
});

function getAllStudents() {
    $.ajax({
        url: "/getAllStudents",
        method: "GET",
        success: response => {
            displayStudents(response);
        },
        error: err => {
            let responseObj = err.responseJSON;
            alert(`ERROR: " ${responseObj.message} " STATUS ${responseObj.status}`);
        }
    })
}

function displayStudents(students) {
    if (students.length > 0) {
        let placeholder = "";
        $.each(students, (index, student) => {
            placeholder +=
                `<tr>
                <input class='student-id' type='hidden' value='${student.id}'>
                <td>${(index + 1)}</td>;
                <td>${student.name} ${student.surname}</td>
                <td>${student.email}</td>
                <td><button class='update-student'>Update</button></td>
                <td><button class='delete-student'>Delete</button></td>
            </tr>`;
        });
        $("#student-placeholder tbody").html(placeholder);
    } else {
        $("#student-div").html("<p>No Students in the system.</p>");
    }
}

$("#add").on("click", () => {
    window.location.href = "/add";
});

$("#student-placeholder").on("click", ".delete-student", function () {
    if (confirm("Click okay to confirm student delete request")) {
        let id = this.parentNode.parentElement.querySelector(".student-id").value;
        $.ajax({
            url: `deleteStudent/${id}`,
            method: "DELETE",
            success: message => {
                alert(`SUCCESS: ${message}`);
                getAllStudents();
            },
            error: err => {
                let responseObj = err.responseJSON;
                alert(`ERROR: "${responseObj.message}" STATUS: ${responseObj.status}`);
            }
        });
    }
});

$("#student-placeholder").on("click", ".update-student", function () {
    let id = this.parentNode.parentElement.querySelector(".student-id").value;
    window.location.href = `/update?uid=${id}`;
});


