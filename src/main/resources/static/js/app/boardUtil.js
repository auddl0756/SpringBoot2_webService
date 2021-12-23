document.addEventListener("DOMContentLoaded", init);

function init(){
    let util = new Util();

}

class Util{
    constructor() {
        this.pageNumber = 0;
        this.searchBtn = $('.searchBtn');
        this.searchBtn.on('click',this.getPostsWithKeyword.bind(this));
    }

    getPostsWithKeyword(){
        const keyword = $('#keyword').val();
        const pageNumber = this.pageNumber;
        this.pageNumber+=1;

        return new Promise(function (resolve, reject) {
            $.ajax({
                url: "/api/v1/posts?pageNumber="+pageNumber+"&keyword="+keyword,
                type: "get",
                success: function (response) {
                    console.log(response);
                    resolve(response);
                },
                error: function () {
                    alert("searched data load failed.");
                }
            });
        });
    }
}