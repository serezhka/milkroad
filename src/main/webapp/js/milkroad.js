/**
 * Created by Sergey on 01.03.2016.
 */
$(document).ready(function () {
    $(".product-edit-image-div input").change(function () {
        showNewProductImage(this);
    });

    $('.product-edit-details-div .selectpicker').each(function () {
        $(this).change(function () {
            if ($(this).val()) {
                addProductDetailField();
            }
        });
    });

    $('.product-edit-details-div a').each(function () {
        $(this).click(function () {
            removeProductDetailField($(this).parent());
        });
    });

    $('#product-data-form').submit(function (e) {
        createOrEditProduct($(this));
        e.preventDefault();
    });

    $('.checkout-order-details input[type=radio][name=shippingMethod]').change(function () {
        if (this.value == 'PICKUP') {
            $('.checkout-order-details #shipping_address').hide();
        } else {
            $('.checkout-order-details #shipping_address').show();
        }
    });

    $('#checkout_form').submit(function () {
        if ($(this).find('input[type=radio][name=shippingMethod]:checked').val() == 'PICKUP') {
            $(this).find('input[type=radio][name="address.id"]:checked').val("");
        }
    });
});

/**
 * Updates order info with orderlist.jsp table row data
 *
 * @param id order id
 */
function updateOrderDetails(id) {
    $.ajax({
            method: "POST",
            url: "/management/updateOrder",
            dataType: "json",
            data: {
                id: id,
                paymentMethod: $("select#paymentMethod_" + id).val(),
                paymentStatus: $("select#paymentStatus_" + id).val(),
                shippingMethod: $("select#shippingMethod_" + id).val(),
                shippingStatus: $("select#shippingStatus_" + id).val()
            }
        })
        .done(function (data) {
            if (data.errors && Object.keys(data.errors).length > 0) {
                $.each(data.errors, function (idx, val) {
                    alert(idx + " : " + val);
                });
                $("tr#order_" + id).effect('highlight', {color: 'red'}, 1000);
            } else {
                $("tr#order_" + id).effect('highlight', {color: 'green'}, 1000);
            }
        });
}

/**
 * Updates category info
 *
 * @param id category id
 */
function updateCategoryDetails(id) {
    $.ajax({
            method: "POST",
            url: "/management/updateCategory",
            dataType: "json",
            data: {
                id: id,
                name: $("input#categoryName_" + id).val(),
                description: $("input#categoryDesc_" + id).val()
            }
        })
        .done(function (data) {
            $("span.error").hide();
            if (data.errors && Object.keys(data.errors).length > 0) {
                $.each(data.errors, function (idx, val) {
                    $("span.error#category_" + idx + "_error").text(val).show();
                });
                $("tr#category_" + id).effect('highlight', {color: 'red'}, 1000);
            } else {
                $("tr#category_" + id).effect('highlight', {color: 'green'}, 1000);
            }
        });
}

/**
 * Updates attribute info
 *
 * @param id attribute id
 */
function updateAttributeDetails(id) {
    $.ajax({
            method: "POST",
            url: "/management/updateAttribute",
            dataType: "json",
            data: {
                id: id,
                name: $("input#attributeName_" + id).val(),
                description: $("input#attributeDesc_" + id).val()
            }
        })
        .done(function (data) {
            $("span.error").hide();
            if (data.errors && Object.keys(data.errors).length > 0) {
                $.each(data.errors, function (idx, val) {
                    $("span.error#attribute_" + idx + "_error").text(val).show();
                });
                $("tr#attribute_" + id).effect('highlight', {color: 'red'}, 1000);
            } else {
                $("tr#attribute_" + id).effect('highlight', {color: 'green'}, 1000);
            }
        });
}

/**
 * Creates new category
 */
function createCategory() {
    $.ajax({
            method: "POST",
            url: "/management/createCategory",
            dataType: "json",
            data: {
                name: $("input#categoryNameNew").val(),
                description: $("input#categoryDescNew").val()
            }
        })
        .done(function (data) {
            $("span.error").hide();
            if (data.errors && Object.keys(data.errors).length > 0) {
                $.each(data.errors, function (idx, val) {
                    $("span.error#category_" + idx + "_error").text(val).show();
                });
                $("tr#category_new").effect('highlight', {color: 'red'}, 1000);
            } else {
                $("tr#category_new").effect('highlight', {color: 'green'}, 1000);
                window.location.reload();
            }
        });
}

/**
 * Creates new attribute
 */
function createAttribute() {
    $.ajax({
            method: "POST",
            url: "/management/createAttribute",
            dataType: "json",
            data: {
                name: $("input#attributeNameNew").val(),
                description: $("input#attributeDescNew").val()
            }
        })
        .done(function (data) {
            $("span.error").hide();
            if (data.errors && Object.keys(data.errors).length > 0) {
                $.each(data.errors, function (idx, val) {
                    $("span.error#attribute_" + idx + "_error").text(val).show();
                });
                $("tr#attribute_new").effect('highlight', {color: 'red'}, 1000);
            } else {
                $("tr#attribute_new").effect('highlight', {color: 'green'}, 1000);
                window.location.reload();
            }
        });
}

function updateAddress(addressID) {
    var form = document.createElement("form");
    form.method = "POST";
    form.action = "/profile/editAddress";

    var address_id = document.createElement("INPUT");
    address_id.name = "id";
    address_id.value = addressID;
    address_id.type = 'hidden';
    form.appendChild(address_id);

    var country = document.createElement("INPUT");
    country.name = "country";
    country.value = $("input#country_" + addressID).val();
    country.type = 'hidden';
    form.appendChild(country);

    var city = document.createElement("INPUT");
    city.name = "city";
    city.value = $("input#city_" + addressID).val();
    city.type = 'hidden';
    form.appendChild(city);

    var postcode = document.createElement("INPUT");
    postcode.name = "postcode";
    postcode.value = $("input#postcode_" + addressID).val();
    postcode.type = 'hidden';
    form.appendChild(postcode);

    var street = document.createElement("INPUT");
    street.name = "street";
    street.value = $("input#street_" + addressID).val();
    street.type = 'hidden';
    form.appendChild(street);

    var building = document.createElement("INPUT");
    building.name = "building";
    building.value = $("input#building_" + addressID).val();
    building.type = 'hidden';
    form.appendChild(building);

    var apartment = document.createElement("INPUT");
    apartment.name = "apartment";
    apartment.value = $("input#apartment_" + addressID).val();
    apartment.type = 'hidden';
    form.appendChild(apartment);

    document.body.appendChild(form);
    form.submit();
}

function createAddress() {
    var form = document.createElement("form");
    form.method = "POST";
    form.action = "/profile/addAddress";

    var country = document.createElement("INPUT");
    country.name = "country";
    country.value = $("input#country_new").val();
    country.type = 'hidden';
    form.appendChild(country);

    var city = document.createElement("INPUT");
    city.name = "city";
    city.value = $("input#city_new").val();
    city.type = 'hidden';
    form.appendChild(city);

    var postcode = document.createElement("INPUT");
    postcode.name = "postcode";
    postcode.value = $("input#postcode_new").val();
    postcode.type = 'hidden';
    form.appendChild(postcode);

    var street = document.createElement("INPUT");
    street.name = "street";
    street.value = $("input#street_new").val();
    street.type = 'hidden';
    form.appendChild(street);

    var building = document.createElement("INPUT");
    building.name = "building";
    building.value = $("input#building_new").val();
    building.type = 'hidden';
    form.appendChild(building);

    var apartment = document.createElement("INPUT");
    apartment.name = "apartment";
    apartment.value = $("input#apartment_new").val();
    apartment.type = 'hidden';
    form.appendChild(apartment);

    document.body.appendChild(form);
    form.submit();
}

function addProductToCart(article_id) {
    var form = document.createElement("form");
    form.method = "POST";
    form.action = "/cart/addProduct";

    var action = document.createElement("INPUT");
    action.name = "action";
    action.value = "addProduct";
    action.type = 'hidden';
    form.appendChild(action);

    var article = document.createElement("INPUT");
    article.name = "article";
    article.value = article_id;
    article.type = 'hidden';
    form.appendChild(article);

    document.body.appendChild(form);
    form.submit();
}

function removeProductFromCart(article_id) {
    var form = document.createElement("form");
    form.method = "POST";
    form.action = "/cart/removeProduct";

    var action = document.createElement("INPUT");
    action.name = "action";
    action.value = "removeProduct";
    action.type = 'hidden';
    form.appendChild(action);

    var article = document.createElement("INPUT");
    article.name = "article";
    article.value = article_id;
    article.type = 'hidden';
    form.appendChild(article);

    document.body.appendChild(form);
    form.submit();
}

function removeProductOnceFromCart(article_id) {
    var form = document.createElement("form");
    form.method = "POST";
    form.action = "/cart/removeProductOnce";

    var action = document.createElement("INPUT");
    action.name = "action";
    action.value = "removeProductOnce";
    action.type = 'hidden';
    form.appendChild(action);

    var article = document.createElement("INPUT");
    article.name = "article";
    article.value = article_id;
    article.type = 'hidden';
    form.appendChild(article);

    document.body.appendChild(form);
    form.submit();
}

function showNewProductImage(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $(".product-edit-image-div img").attr("src", e.target.result);
        };
        reader.readAsDataURL(input.files[0]);
    }
}

function addProductDetailField() {
    var allSelects = updateProductDetailFields();
    var lastSelect = $(allSelects).last();
    if ($(lastSelect).find("option:selected").val()) {
        var newSelectDiv = $(lastSelect).parent().parent().clone();
        $(newSelectDiv).find(".bootstrap-select").remove();
        $(newSelectDiv).find("input").val("");
        $(newSelectDiv).find("a").click(function () {
            removeProductDetailField($(newSelectDiv));
        });
        var newSelect = $(lastSelect).clone();
        $(newSelect).attr("id", "parameter_" + $(allSelects).size());
        $(newSelect).val("");
        $(newSelect).change(function () {
            if ($(this).val()) {
                addProductDetailField();
            }
        });
        $(newSelect).prependTo($(newSelectDiv));
        $(newSelectDiv).appendTo($(lastSelect).parent().parent().parent());
        $(newSelect).selectpicker();
    }
}

function removeProductDetailField(field) {
    if ($('.product-edit-details-div .selectpicker').size() > 1) {
        $(field).remove();
    } else {
        $(field).find(".selectpicker option").first().attr("selected", true);
        $(field).find(".selectpicker").selectpicker("refresh");
    }
    updateProductDetailFields();
    addProductDetailField();
}

function updateProductDetailFields() {
    var allSelects = $('.product-edit-details-div .selectpicker');
    $(allSelects).each(function (idx, select) {
        $(select).attr("id", "parameter_" + idx);
        if (idx != ($(allSelects).size() - 1)) {
            $(select).attr("name", "parameters[" + idx + "].attribute.id");
            $(select).parent().parent().find("input").attr("name", "parameters[" + idx + "].value");
        } else {
            $(select).removeAttr("name");
            $(select).parent().parent().find("input").removeAttr("name");
        }
    });
    return allSelects;
}

function createOrEditProduct(form) {
    var formData = new FormData($(form)[0]);
    $.ajax({
        url: $(form).attr("action"),
        type: $(form).attr("method"),
        data: formData,
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success: function (data) {
            $("span.error").hide();
            if (data.errors && Object.keys(data.errors).length > 0) {
                $.each(data.errors, function (idx, val) {
                    $("span.error#" + idx + "_error").text(val).show();
                });
                $("input[name='" + idx + "']").effect('highlight', {color: 'red'}, 1000);
            } else {
                window.location.href="/editProducts";
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.response);
        }
    });
    return false;
}
