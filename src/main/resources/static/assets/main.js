function getToken() {
  return localStorage.getItem('jwtToken');
}

function setToken(token) {
  localStorage.setItem('jwtToken', token);
}

function clearToken() {
  localStorage.removeItem('jwtToken');
}

function isAuthenticated() {
  return !!getToken();
}

function showLoginModal(show) {
  const modal = document.getElementById('loginModal');
  if (modal) modal.style.display = show ? 'flex' : 'none';
}

function updateProfile(email) {
  document.getElementById('profileEmail').textContent = email || '';
}

function showDashboard(isAuth) {
  document.querySelectorAll('.dashboard-header, .summary-row, .nav-grid, .row').forEach(el => {
    el.style.display = isAuth ? '' : 'none';
  });
  document.getElementById('profileNav').style.display = isAuth ? '' : 'none';
}

function fetchAndRender() {
  const token = getToken();
  if (!token) return;
  axios.get('http://localhost:8080/fms/users/all', { headers: { Authorization: 'Bearer ' + token } })
    .then(res => {
      let html = '';
      res.data.forEach(user => {
        html += `<li>${user.name} (${user.role})</li>`;
      });
      document.getElementById('usersList').innerHTML = html;
      document.getElementById('summaryDrivers').textContent = res.data.length;
    });
  axios.get('http://localhost:8080/fms/vehicles', { headers: { Authorization: 'Bearer ' + token } })
    .then(res => {
      let html = '';
      res.data.forEach(vehicle => {
        html += `<li>${vehicle.vehicleNumber} - ${vehicle.model}</li>`;
      });
      document.getElementById('vehiclesList').innerHTML = html;
      document.getElementById('summaryVehicles').textContent = res.data.length;
    });
  axios.get('http://localhost:8080/fms/locations', { headers: { Authorization: 'Bearer ' + token } })
    .then(res => {
      let html = '';
      res.data.forEach(loc => {
        html += `<li>${loc.latitude}, ${loc.longitude} at ${loc.timestamp}</li>`;
      });
      document.getElementById('locationsList').innerHTML = html;
    });
}

// Add axios interceptor to attach JWT to all requests
axios.interceptors.request.use(config => {
  const token = getToken();
  if (token) {
    config.headers.Authorization = 'Bearer ' + token;
  }
  return config;
});

// PDF generation button handler
window.addEventListener('DOMContentLoaded', function() {
  const pdfBtn = document.getElementById('generatePdfBtn');
  if (pdfBtn) {
    pdfBtn.addEventListener('click', async function() {
      try {
        const response = await axios.get('http://localhost:8080/fms/locations/report/pdf', {
          responseType: 'blob'
        });
        const url = window.URL.createObjectURL(new Blob([response.data], { type: 'application/pdf' }));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', 'FleetReport.pdf');
        document.body.appendChild(link);
        link.click();
        link.remove();
        window.URL.revokeObjectURL(url);
      } catch (err) {
        alert('Failed to generate PDF. Please try again.');
      }
    });
  }
});

window.onload = function () {
  if (!isAuthenticated()) {
    window.location.href = 'login.html';
    return;
  }
  showDashboard(true);
  fetchAndRender();
  // Optionally decode JWT to get email
  try {
    const payload = JSON.parse(atob(getToken().split('.')[1]));
    updateProfile(payload.sub || payload.username || '');
  } catch {}

  // Logout handler
  const logoutBtn = document.getElementById('logoutBtn');
  if (logoutBtn) {
    logoutBtn.onclick = function () {
      clearToken();
      window.location.href = 'login.html';
      updateProfile('');
    };
  }
};
  