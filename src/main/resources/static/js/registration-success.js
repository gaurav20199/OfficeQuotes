// Timer functionality for resend button
let countdown;
const timerDisplay = document.getElementById('timer');
const countdownDisplay = document.getElementById('countdown');
const resendButton = document.querySelector('.resend-btn');

function startTimer(seconds) {
    clearInterval(countdown);

    const now = Date.now();
    const then = now + seconds * 1000;

    displayTimeLeft(seconds);

    countdown = setInterval(() => {
        const secondsLeft = Math.round((then - Date.now()) / 1000);

        if(secondsLeft < 0) {
            clearInterval(countdown);
            enableResendButton();
            return;
        }

        displayTimeLeft(secondsLeft);
    }, 1000);
}

function displayTimeLeft(seconds) {
    countdownDisplay.textContent = seconds;
    timerDisplay.classList.add('visible');
}

function enableResendButton() {
    resendButton.disabled = false;
    timerDisplay.classList.remove('visible');
}

function handleResend() {
    resendButton.disabled = true;
    startTimer(60);

    // Here you would typically make an API call to resend the verification email
    console.log('Resending verification email...');
}

// Initialize the page
document.addEventListener('DOMContentLoaded', () => {
    resendButton.disabled = false;
    timerDisplay.classList.remove('visible');
});