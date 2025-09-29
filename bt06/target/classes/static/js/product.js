$(document).ready(function () {
    loadProducts();

    function loadProducts() {
        $.get("/api/products", function (data) {
            let rows = "";
            data.forEach(p => {
                rows += `
                <tr>
                    <td>${p.id}</td>
                    <td>${p.name}</td>
                    <td>${p.description}</td>
                    <td>${p.price}</td>
                    <td>${p.category ? p.category.name : ''}</td>
                    <td>
                        <button onclick="deleteProduct(${p.id})">Delete</button>
                    </td>
                </tr>`;
            });
            $("#productTable").html(rows);
        });
    }

    $("#productForm").submit(function (e) {
        e.preventDefault();
        const product = {
            name: $("#name").val(),
            description: $("#description").val(),
            price: parseFloat($("#price").val()),
            category: { id: parseInt($("#categoryId").val()) }
        };
        $.ajax({
            url: "/api/products",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(product),
            success: function () {
                loadProducts();
                $("#name").val('');
                $("#description").val('');
                $("#price").val('');
                $("#categoryId").val('');
            }
        });
    });
});

function deleteProduct(id) {
    $.ajax({
        url: "/api/products/" + id,
        method: "DELETE",
        success: function () {
            alert("Deleted successfully");
            location.reload();
        }
    });
}
