console.log("Hello Javascript!")
getUploadList()

function uploadFile(file) {
    const formData = new FormData();
    formData.append("file", file);

    fetch("http://localhost:8080/api/files/upload", {
        method: "POST",
        body: formData
    })
        .then(response => response.json())
        .then(data => {
            try {
                const uploadList = document.querySelector("#uploadList");
                uploadList.insertAdjacentHTML("beforeend",
                    `
                            <div class="upload-list__item">
                                <img src="${data.url}" title="${data.fileName}" alt="..." width="500px">
                            </div>
                        `)

            } catch (e) {
                return e
            }
        })

}

function getUploadList() {
    fetch("http://localhost:8080/api/files")
        .then(response => response.json())
        .then(data => {
            try {
                const uploadList = document.querySelector("#uploadList");
                data.forEach(img => {
                    uploadList.insertAdjacentHTML("beforeend",
                        `
                            <div class="upload-list__item">
                                <img src="${img.url}" title="${img.fileName}" alt="..." width="500px">
                            </div>
                        `)
                });
            } catch (e) {
                return e
            }
        });
}