import { request } from "http";

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

export default async function filterImage(image, filter) {
  var url64 = null
  //Converts the uploaded image into base64
  image = document.getElementById("image");
  await toDataURL(image.src, 'image/JPEG')
  .then(function(dataURL) {
    // Handle the data URL here
    url64 = dataURL
  })
  url64 = url64.slice(url64.indexOf(",") + 1)
  //console.log(url64)
  //Create a JSON object of the url
  //let splitUrl = url64.slice(url64.indexOf(",") + 1)
  // const jsonStr = "{ " + '"body" : "' + splitUrl + '" }'
  // const jsonObj = JSON.parse(jsonStr)

  //to test that image is converted
  //console.log(url64)
  //console.log(jsonObj)


  // Define the fetch options
  const options = {
    method: 'POST',
    headers: {
      'Content-Type': 'text/plain' // Set the Content-Type header to indicate plain text
    },
    body: url64 // Pass the string directly as the body of the request
  };

  //TODO: put in environment variables
  var url = "http://localhost:3001/" + filter

  // Send the POST request
  fetch(url, options)
  .then(response => {
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    return response.text(); // Parse the response body as text
  })
  .then(data => {
    //Remove unnecessary data from string
    data = data.slice(data.indexOf(":") + 2);
    data = data.slice(0, -14);

    //Change image to blurred image
    image.src = data;
  })
  .catch(error => {
    console.error('There was a problem with the POST request:', error);
});

}