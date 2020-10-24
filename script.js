const inputCode = document.getElementById('inputCode');
const outputCode = document.getElementById('outputCode');

async function postData(url, data) {
    const response = await fetch(url, {
      method: 'POST', // *GET, POST, PUT, DELETE, etc.
      cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data) // body data type must match "Content-Type" header
    });
    console.log(response)
    return response.json(); // parses JSON response into native JavaScript objects
  }

async function convert() {
    console.log("before send "+ inputCode.value)
    return postData(' https://h63frmmax9.execute-api.us-east-1.amazonaws.com/dev/ScalaToJavaConverter', { scalaCode: inputCode.value })
    .then(data => {
      console.log(data); 
      outputCode.value = data.javaCode;
    });
}