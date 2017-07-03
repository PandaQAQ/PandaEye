function initClick()
{
    var objects = document.getElementsByTagName("img");
    for(var i=0;i<objects.length;i++)
    {
        objects[i].onclick= function (){
            window.JavaScriptFunction.getUrl(this.src);
        }
    }
}