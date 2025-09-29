$(document).ready(function () {
    loadCategories();

    // Load list
    function loadCategories() {
        $.get("/api/categories", function (data) {
            let rows = "";
            data.forEach(c => {
                rows += `
                <tr>
                    <td>${c.id}</td>
                    <td>${c.name}</td>
                    <td>${c.description}</td>
                    <td>
                        <button onclick="deleteCategory(${c.id})">Delete</button>
                    </td>
                </tr>`;
            });
            $("#categoryTable").html(rows);
        });
    }

    // Add new
    $("#categoryForm").submit(function (e) {
        e.preventDefault();
        const category = {
            name: $("#name").val(),
            description: $("#description").val()
        };
        $.ajax({
            url: "/api/categories",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(category),
            success: function () {
                loadCategories();
                $("#name").val('');
                $("#description").val('');
            }
        });
    });
});

function deleteCategory(id) {
    $.ajax({
        url: "/api/categories/" + id,
        method: "DELETE",
        success: function () {
            alert("Deleted successfully");
            location.reload();
        }
    });
}
