//Creates a canvas, converts to dataUrl then converted to base64
function toDataURL(src, outputformat) {
    return new Promise(function(resolve) {
      var img = new Image();
      img.crossOrigin = 'Anonymous';
      img.onload = function() {
        var canvas = document.createElement('CANVAS');
        var ctx = canvas.getContext('2d');
        var dataURL;
        canvas.height = this.naturalHeight;
        canvas.width = this.naturalWidth;
        ctx.drawImage(this, 0, 0);
        dataURL = canvas.toDataURL(outputformat);
        resolve(dataURL);
      };
      img.src = src;
      if (img.complete || img.complete === undefined) {
        img.src = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw==";
        img.src = src;
      }
    });
  }

  export default async function reqThreshold(image) {
    var url64 = null
    //Converts the uploaded image into base64
    image = document.getElementById("image");
    await toDataURL(image.src, 'image/JPEG')
    .then(function(dataURL) {
      // Handle the data URL here
      url64 = dataURL
    })
  
    //Create a JSON object of the url
    let splitUrl = url64.slice(url64.indexOf(",") + 1)
    const jsonStr = "{ " + '"body" : "' + splitUrl + '" }'
    const jsonObj = JSON.parse(jsonStr)
  
    //to test that image is converted
    //console.log(url64)
    console.log(jsonObj)
  
  
    //get secure url from server
  
    //POST image directly to s3 bucket
  
    //POST request to server to store any extra data
  
    //Get response when image is done
    //image.src = "https://images-dv1566.s3.amazonaws.com/829621ba-a233-4e66-8890-30678c6e9f81.png"
  
    //show download button
  }