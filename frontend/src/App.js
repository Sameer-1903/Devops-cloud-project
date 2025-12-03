import React, { useEffect, useState } from 'react';

function App() {
  const [msg, setMsg] = useState('Loading...');
  useEffect(() => {
    const base = process.env.REACT_APP_API_BASE || '';
    fetch(`${base}/api/hello`)
      .then(r => r.json())
      .then(d => setMsg(d.message))
      .catch(e => setMsg('Error: ' + e.message));
  }, []);
  return (
    <div style={{ fontFamily: 'Arial', padding: 24 }}>
      <h1>DevOps Cloud Project</h1>
      <p>Backend says: <strong>{msg}</strong></p>
    </div>
  );
}

export default App;
