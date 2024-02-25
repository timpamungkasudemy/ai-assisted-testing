function parallelCall() {
  // Make parallel API calls
  Promise.all([
    fetch('/alphamart/api/basic/who-am-i'),
    fetch('/alphamart/api/basic/custom-delay?delay=2000&identifier=custom-delay-1'),
    fetch('/alphamart/api/basic/fast'),
    fetch('/alphamart/api/basic/custom-delay?delay=2000&identifier=custom-delay-2'),
    fetch('/alphamart/api/basic/time'),
    fetch('/alphamart/api/basic/custom-delay?delay=2000&identifier=custom-delay-3'),
  ])
    .then(responses => Promise.all(responses.map(response => response.json())))
    .then(results => {
      // Display each API result in the <div id="result"></div>
      const resultDiv = document.getElementById('result');
      results.forEach(result => {
        const resultText = document.createTextNode(result.message);
        resultDiv.appendChild(resultText);
        resultDiv.appendChild(document.createElement('br'));
      });
    })
    .catch(error => {
      console.error('Error:', error);
    });
}

async function sequentialCall() {
  try {
    const urls = [
      '/alphamart/api/basic/who-am-i',
      '/alphamart/api/basic/custom-delay?delay=2000&identifier=custom-delay-1',
      '/alphamart/api/basic/fast',
      '/alphamart/api/basic/custom-delay?delay=2000&identifier=custom-delay-2',
      '/alphamart/api/basic/time',
      '/alphamart/api/basic/custom-delay?delay=2000&identifier=custom-delay-3',
    ];

    const resultDiv = document.getElementById('result');

    for (const url of urls) {
      const response = await fetch(url);
      const result = await response.json();

      const resultText = document.createTextNode(result.message);
      resultDiv.appendChild(resultText);
      resultDiv.appendChild(document.createElement('br'));
    }
  } catch (error) {
    console.error('Error:', error);
  }
}
