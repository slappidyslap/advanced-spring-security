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
                const cardGroup = document.querySelector("#cardGroup");
                cardGroup.insertAdjacentHTML("beforeend",
                    `
                            <div class="card">
                                <img src="${data.url}" class="card-img-top" alt="...">
                                <div class="card-body">
                                    <h5 class="card-title">${data.fileName}</h5>
                                    <p class="card-text">s content is a little bit longer.</p>
                                </div>
                                <div class="card-footer">
                                    <small class="text-muted">Last updated 3 mins ago</small>
                                </div>
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
                const cardGroup = document.querySelector("#cardGroup");
                data.forEach(img => {
                    cardGroup.insertAdjacentHTML("beforeend",
                        `
                            <div class="card">
                                <img src="${img.url}" class="card-img-top" alt="...">
                                <div class="card-body">
                                    <h5 class="card-title">${img.fileName}</h5>
                                    <p class="card-text">s content is a little bit longer.</p>
                                </div>
                                <div class="card-footer">
                                    <small class="text-muted">Last updated 3 mins ago</small>
                                </div>
                            </div>
                        `)
                });
            } catch (e) {
                return e
            }
        });
}