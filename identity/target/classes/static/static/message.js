var message=function (res) {
    if (res!==Object){
        this.from=res.mine.id
        this.to=res.to.id
        this.type=res.to.type
        this.content=res.mine.content
    }
    this.token=sessionStorage .getItem("token")

}