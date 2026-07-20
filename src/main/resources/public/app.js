const form = document.querySelector('#scan-form');
const pathInput = document.querySelector('#project-path');
const thresholdInput = document.querySelector('#threshold');
const thresholdValue = document.querySelector('#threshold-value');
const scanButton = document.querySelector('#scan-button');
const errorBanner = document.querySelector('#error-banner');
const results = document.querySelector('#results');
const summary = document.querySelector('#summary');
const resultsBody = document.querySelector('#results-body');

thresholdInput.addEventListener('input', () => {
  thresholdValue.value = Number(thresholdInput.value).toFixed(2);
});

form.addEventListener('submit', async (event) => {
  event.preventDefault();
  setLoading(true);
  clearError();
  results.hidden = true;

  try {
    const response = await fetch('/api/scan', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        path: pathInput.value.trim(),
        threshold: Number(thresholdInput.value)
      })
    });
    const payload = await response.json();

    if (!response.ok) {
      throw new Error(payload.error || 'The scan could not be completed.');
    }

    renderResults(payload);
  } catch (error) {
    showError(error.message || 'The scan could not be completed.');
  } finally {
    setLoading(false);
  }
});

function setLoading(isLoading) {
  scanButton.disabled = isLoading;
  scanButton.textContent = isLoading ? 'Scanning…' : 'Scan';
}

function clearError() {
  errorBanner.hidden = true;
  errorBanner.textContent = '';
}

function showError(message) {
  errorBanner.textContent = message;
  errorBanner.hidden = false;
}

function renderResults(payload) {
  summary.textContent = `${payload.methodCount} methods found across ${payload.fileCount} files; ${payload.duplicates.length} duplicate pairs found.`;
  resultsBody.replaceChildren();

  for (const duplicate of payload.duplicates) {
    const row = document.createElement('tr');
    appendCell(row, duplicate.firstMethod);
    appendCell(row, `${duplicate.firstFile}:${duplicate.firstLine}`);
    appendCell(row, duplicate.secondMethod);
    appendCell(row, `${duplicate.secondFile}:${duplicate.secondLine}`);
    appendCell(row, `${duplicate.similarityPercent}%`);
    resultsBody.append(row);
  }

  results.hidden = false;
}

function appendCell(row, value) {
  const cell = document.createElement('td');
  cell.textContent = value;
  row.append(cell);
}
