// ********************************************* Student Process *****************************************************//

$(document).on("click", "#add-student-btn", function (event) {
    event.preventDefault();
    $.ajax({
        type: "GET",
        url: $(this).data("url"),
        headers: {
            "X-Requested-With": "XMLHttpRequest"
        },
        success: function (data) {
            $(".content-area").html(data);
        },
        error: processError
    });
});

$(document).on("submit", "#createStudentForm", function (event) {
    event.preventDefault();
    let form = $(this);
    $.ajax({
        type: form.attr("method"),
        url: form.attr("action"),
        headers: {
            "X-Requested-With": "XMLHttpRequest"
        },
        data: form.serialize(),
        success: function (data) {
            $(".content-area").html(data);
            let a_tag = $(".page-number").last();
            a_tag.css({
                "background-color": "#FF6636",
                "color": "#FFFFFF"
            });
        },
        error: processError
    });

    $(document).on('click', function (event) {
        if (!$(event.target).closest('.error-message').length) {
            $(".error-message").hide();
        }
    });
});

$(document).on("click", ".student-detail", function (event) {
    event.preventDefault();
    let stdId = $(this).data("std-id");
    $.ajax({
        type: "GET",
        url: "/admin/student/edit/" + stdId + "?page=" + $(this).data("page"),
        headers: {
            "X-Requested-With": "XMLHttpRequest"
        },
        success: function (data) {
            $(".content-area").html(data);
        },
        error: processError
    });
});

$(document).on("submit", "#studentForm", function (event) {
    event.preventDefault();
    let form = $(this);
    const page = $(this).data("page");
    console.log(page)
    let formData = new FormData(form[0]);
    let avatarFile = $('#avatarInput')[0].files[0];
    let degreeFile = $('#degreeInput')[0].files[0];
    if (avatarFile) {
        formData.append("avatar", avatarFile);
    }
    if (degreeFile) {
        formData.append("degree", degreeFile);
    }

    $.ajax({
        type: form.attr("method"),
        url: form.attr("action"),
        headers: {
            "X-Requested-With": "XMLHttpRequest"
        },
        data: formData,
        contentType: false,
        processData: false,
        success: function (data) {
            $(".content-area").html(data);
            if (data.includes("page-number")) {
                document.querySelectorAll(".page-number").forEach((element) => {
                    element.style.backgroundColor = "#FFFFFF";
                    element.style.color = "#000000";
                });
                document.getElementById(page).style.backgroundColor = "#FF6636";
                document.getElementById(page).style.color = "#FFFFFF";
            }
        },
        error: processError
    });

    $(document).on('click', function (event) {
        if (!$(event.target).closest('.error-message').length) {
            $(".error-message").hide();
        }
    });
});

$(document).on("click", "#std-delete-btn", function (event) {
    event.preventDefault();
    let studentId = $(this).data("student-id");
    let page = $(this).data("page");
    $.ajax({
        type: "GET",
        url: "/admin/student/delete/" + studentId + "?page=" + page,
        headers: {
            "X-Requested-With": "XMLHttpRequest"
        },
        success: function (data) {
            $(".content-area").html(data);
            if (data.includes("page-number")) {
                document.querySelectorAll(".page-number").forEach((element) => {
                    element.style.backgroundColor = "#FFFFFF";
                    element.style.color = "#000000";
                });
                document.getElementById(page).style.backgroundColor = "#FF6636";
                document.getElementById(page).style.color = "#FFFFFF";
            }
        },
        error: processError
    });
});

// *************************************************************//

$(document).on("click", "#course-edit-btn", function (event) {
    event.preventDefault();
    let courseId = $(this).data("course-id");
    let page = $(this).data("page");
    $.ajax({
        type: "GET",
        url: "/course/admin/update/" + courseId + "?page=" + page,
        headers: {
            "X-Requested-With": "XMLHttpRequest"
        },
        success: function (data) {
            $(".content-area").html(data);
        },
        error: processError
    });
});

$(document).on("click", "#course-delete-btn", function (event) {
    event.preventDefault();
    let courseId = $(this).data("course-id");
    $.ajax({
        type: "GET",
        url: "/course/delete/" + courseId,
        headers: {
            "X-Requested-With": "XMLHttpRequest"
        },
        success: function (data) {
            $(".content-area").html(data);
        },
        error: processError
    });
});

$(document).on("click", "#addClassBtn", function (event) {
    event.preventDefault();
    $.ajax({
        type: "GET",
        url: "/admin/class/create",
        headers: {
            "X-Requested-With": "XMLHttpRequest"
        },
        success: function (data) {
            $(".content-area").html(data);
        },
        error: processError
    });
});

$(document).on("click", "#class-delete-btn", function (event) {
    event.preventDefault();
    let classId = $(this).data("class-id");
    $.ajax({
        type: "GET",
        url: "/admin/class/delete/" + classId,
        headers: {
            "X-Requested-With": "XMLHttpRequest"
        },
        success: function (data) {
            $(".content-area").html(data);
        },
        error: processError
    });
});
//
//
$(document).on("submit", "#classForm", function (event) {
    event.preventDefault();
    let form = $(this);
    $.ajax({
        type: form.attr("method"),
        url: form.attr("action"),
        headers: {
            "X-Requested-With": "XMLHttpRequest"
        },
        data: form.serialize(),
        success: function (data) {
            $(".content-area").html(data);
            let a_tag = $(".page-number").last();
            a_tag.css({
                "background-color": "#FF6636",
                "color": "#FFFFFF"
            });
        },
        error: processError
    });

    $(document).on('click', function (event) {
        if (!$(event.target).closest('.error-message').length) {
            $(".error-message").hide();
        }
    });
});
//
$(document).on("click", "#class-edit-btn", function (event) {
    event.preventDefault();
    let classId = $(this).data("class-id");
    let page = $(this).data("page");
    $.ajax({
        type: "GET",
        url: "/admin/class/edit/" + classId + "?page=" + page,
        headers: {
            "X-Requested-With": "XMLHttpRequest"
        },
        success: function (data) {
            $(".content-area").html(data);
        },
        error: processError
    });
});

/////////////////////////////////////////////////////////////////////////////////////////////////////

$(document).on("click", "#user-delete-btn", function (event) {
    event.preventDefault();
    let userId = $(this).data("user-id");
    $.ajax({
        type: "GET",
        url: "/user/delete/" + userId,
        headers: {
            "X-Requested-With": "XMLHttpRequest"
        },
        success: function (data) {
            $(".content-area").html(data);
        },
        error: processError
    });
});
/////////////////////////////////////////////////////////////////////////////////////////////////////
$(document).on("submit", "#editClassForm", function (event) {
    event.preventDefault();
    let form = $(this);
    let page = $(this).data("page");
    console.log(page);
    $.ajax({
        type: form.attr("method"),
        url: form.attr("action"),
        headers: {
            "X-Requested-With": "XMLHttpRequest"
        },
        data: form.serialize(),
        success: function (data) {
            $(".content-area").html(data);
            document.querySelectorAll(".page-number").forEach((element) => {
                element.style.backgroundColor = "#FFFFFF";
                element.style.color = "#000000";
            });
            document.getElementById(page).style.backgroundColor = "#FF6636";
            document.getElementById(page).style.color = "#FFFFFF";
        },
        error: processError
    });
});

$(document).on("click", "#add-std-to-class", function (event) {
    event.preventDefault();
    let classId = $(this).data("class-id");
    let page = $(this).data("page");
    $.ajax({
        type: "GET",
        url: "/admin/class/add-student/" + classId + "?page=" + page,
        headers: {
            "X-Requested-With": "XMLHttpRequest"
        },
        success: function (data) {
            $(".content-area").html(data);
            if (data.includes("page-number")) {
                document.querySelectorAll(".page-number").forEach((element) => {
                    element.style.backgroundColor = "#FFFFFF";
                    element.style.color = "#000000";
                });
                document.getElementById('0').style.backgroundColor = "#FF6636";
                document.getElementById('0').style.color = "#FFFFFF";
            }
        },
        error: processError
    });
})

$(document).on("submit", "#add-std-to-class-form", function (event) {
   event.preventDefault();
    let form = $(this);
    let page = $(this).data("page");
    $.ajax({
        type: form.attr("method"),
        url: form.attr("action"),
        headers: {
            "X-Requested-With": "XMLHttpRequest"
        },
        data: form.serialize(),
        success: function (data) {
            $(".content-area").html(data);
            document.querySelectorAll(".page-number").forEach((element) => {
                element.style.backgroundColor = "#FFFFFF";
                element.style.color = "#000000";
            });
            document.getElementById(page).style.backgroundColor = "#FF6636";
            document.getElementById(page).style.color = "#FFFFFF";
        },
        error: processError
    });
});
/////////////////////////////////////////////////////////////////////////////////////////////////////
$(document).on("click", "#addCourseBtn", function (event) {
    event.preventDefault();
    $.ajax({
        type: "GET",
        url: "/course/create",
        headers: {
            "X-Requested-With": "XMLHttpRequest"
        },
        success: function (data) {
            $(".content-area").html(data);
        },
        error: processError
    });
});
/////////////////////////////////////////////////////////////////////////////////////////////////////
$(document).on("submit", "#courseForm", function (event) {
    event.preventDefault();
    let form = $(this);
    let page = $(this).data("page");
    $.ajax({
        type: form.attr("method"),
        url: form.attr("action"),
        headers: {
            "X-Requested-With": "XMLHttpRequest"
        },
        data: form.serialize(),
        success: function (data) {
            $(".content-area").html(data);
            document.querySelectorAll(".page-number").forEach((element) => {
                element.style.backgroundColor = "#FFFFFF";
                element.style.color = "#000000";
            });
            document.getElementById(page).style.backgroundColor = "#FF6636";
            document.getElementById(page).style.color = "#FFFFFF";
        },
        error: processError
    });
});
/////////////////////////////////////////////////////////////////////////////////////////////////////
$(document).on("click", "#add-std-btn", function (event) {
    event.preventDefault();
    let courseId = $(this).data("course-id");
    const page = $(this).data("page");
    $.ajax({
        type: "GET",
        url: "/course/admin/add-student/" + courseId + "?page=" + page,
        headers: {
            "X-Requested-With": "XMLHttpRequest"
        },
        success: function (data) {
            $(".content-area").html(data);
            document.querySelectorAll(".page-number").forEach((element) => {
                element.style.backgroundColor = "#FFFFFF";
                element.style.color = "#000000";
            });
            document.getElementById("0").style.backgroundColor = "#FF6636";
            document.getElementById("0").style.color = "#FFFFFF";
        },
        error: processError
    });
});
/////////////////////////////////////////////////////////////////////////////////////////////////////
$(document).on("click", ".course-detail-", function (event) {
    event.preventDefault();
    let courseId = $(this).data("course-id");
    $.ajax({
        type: "GET",
        url: "/course/trainer/detail/" + courseId + "?page=0",
        headers: {
            "X-Requested-With": "XMLHttpRequest"
        },
        success: function (data) {
            $(".content-area").html(data);
            if (data.includes("page-number")) {
                document.querySelectorAll(".page-number").forEach((element) => {
                    element.style.backgroundColor = "#FFFFFF";
                    element.style.color = "#000000";
                });
                document.getElementById('0').style.backgroundColor = "#FF6636";
                document.getElementById('0').style.color = "#FFFFFF";
            }
        },
        error: processError
    });
});
/////////////////////////////////////////////////////////////////////////////////////////////////////
$(document).ready(function () {
    $(".menu-item").click(function (event) {
        event.preventDefault();
        const url = $(this).data("url");
        const page = $(this).data("page");
        const id = $(this).attr("id");
        console.log("Loading content from " + url);

        $.ajax({
            url: url,
            type: "GET",
            headers: {
                "X-Requested-With": "XMLHttpRequest"
            },
            success: function (data) {
                $(".content-area").html(data);
                document.querySelectorAll(".menu-item").forEach((item) => {
                    item.style.backgroundColor = "transparent";
                    item.style.borderLeftColor = "transparent";
                });

                document.getElementById(id).style.backgroundColor = "#555";
                document.getElementById(id).style.borderLeftColor = "#fd7e14";

                if (data.includes("page-number")) {
                    document.querySelectorAll(".page-number").forEach((element) => {
                        element.style.backgroundColor = "#FFFFFF";
                        element.style.color = "#000000";
                    });
                    document.getElementById(page).style.backgroundColor = "#FF6636";
                    document.getElementById(page).style.color = "#FFFFFF";
                }
            },
            error: processError
        });
    });
});

$(document).on("click", ".page-number", function (event) {
    event.preventDefault();
    let url = $(this).data("url");
    let page = $(this).data("page");
    console.log("Loading content from " + url);
    $.ajax({
        type: "GET",
        url: url,
        headers: {
            "X-Requested-With": "XMLHttpRequest"
        },
        success: function (data) {
            $(".content-area").html(data);
            document.querySelectorAll(".page-number").forEach((element) => {
                element.style.backgroundColor = "#FFFFFF";
                element.style.color = "#000000";
            });
            document.getElementById(page).style.backgroundColor = "#FF6636";
            document.getElementById(page).style.color = "#FFFFFF";
        },
        error: processError
    });
});

$(document).on("submit", ".scoreForm", function (event) {
    event.preventDefault();
    let form = $(this);
    $.ajax({
        type: form.attr("method"),
        url: form.attr("action"),
        headers: {
            "X-Requested-With": "XMLHttpRequest"
        },
        data: form.serialize(),
        success: function (data) {
            alert("Submit success");
            $(".content-area").html(data);
        },
        error: processError
    });
});

$(document).on("click", ".cancel-btn", function (event) {
    event.preventDefault();
    let url = $(this).data("url");
    let page = $(this).data("page");
    $.ajax({
        type: "GET",
        url: url,
        headers: {
            "X-Requested-With": "XMLHttpRequest"
        },
        success: function (data) {
            $(".content-area").html(data);
            document.querySelectorAll(".page-number").forEach((element) => {
                element.style.backgroundColor = "#FFFFFF";
                element.style.color = "#000000";
            });
            document.getElementById(page).style.backgroundColor = "#FF6636";
            document.getElementById(page).style.color = "#FFFFFF";
        },
        error: processError
    });
});

function previewAvatar() {
    const input = document.getElementById('avatarInput');
    const preview = document.getElementById('avatarPreview');
    const degreeInput = document.getElementById('degreeInput');
    const degreePreview = document.getElementById('degreePreview');

    const file = input.files[0];
    const degreeFile = degreeInput.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            preview.src = e.target.result;
        };
        reader.readAsDataURL(file);
    }

    if (degreeFile) {
        const reader = new FileReader();
        reader.onload = function (e) {
            degreePreview.src = e.target.result;
        };
        reader.readAsDataURL(degreeFile);
    }
}

function showModalAvatar() {
    const modal = document.getElementById('imageModal');
    const modalImg = document.getElementById('modalImage');
    modalImg.src = document.getElementById('avatarPreview').src;
    modal.style.display = 'flex';
}

function showModalDegree() {
    const modal = document.getElementById('imageModal');
    const modalImg = document.getElementById('modalImage');
    modalImg.src = document.getElementById('degreePreview').src;
    modal.style.display = 'flex';
}

function closeModal() {
    const modal = document.getElementById('imageModal');
    modal.style.display = 'none';
}

const processError = (jqXHR, textStatus, error) => {
    if (jqXHR.status === 401) {
        alert("Your session has expired. Please login again to continue.");
        window.location.href = "/login";
    } else {
        console.error("Error loading content:", textStatus, error);
        alert("Có lỗi xảy ra khi tải nội dung. Vui lòng thử lại!");
    }
}

//**********************************************************************************************************************
