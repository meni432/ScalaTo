const editor = ace.edit("editor");
editor.setTheme("ace/theme/monokai");
editor.session.setMode("ace/mode/scala");

const output = ace.edit("outputCode");
output.setTheme("ace/theme/monokai");
output.session.setMode("ace/mode/java");

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
    console.log("before send "+ editor.getValue())
    return postData(' https://h63frmmax9.execute-api.us-east-1.amazonaws.com/dev/ScalaToJavaConverter', { scalaCode: editor.getValue() })
    .then(data => {
      console.log(data); 
      outputCode.value = data.javaCode;
      output.session.setValue(data.javaCode);
    });
}

