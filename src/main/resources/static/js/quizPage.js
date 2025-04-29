let quizData = [];
let currentQuestion = 0;
let score = 0;
let quizEnded = false;

const questionElement = document.getElementById('question');
const questionImageElement = document.getElementById('question-image');
const optionsContainer = document.getElementById('options-container');
const resultContainer = document.getElementById('result-container');
const resultText = document.getElementById('result-text');
const funFactElement = document.getElementById('fun-fact');
const nextButton = document.getElementById('next-btn');
const scoreElement = document.getElementById('score');
const progressBar = document.getElementById('progress-bar');

const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const headerName = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');


async function fetchQuizData() {
    try {
        const response = await fetch('/quiz/data'); // Ensure this endpoint returns quiz data
        quizData = await response.json();
        initQuiz();
    } catch (error) {
        console.error('Error fetching quiz data:', error);
    }
}

function handleLogout() {
    if (confirm('Are you sure you want to logout?')) {
        document.getElementById('logoutForm').submit();
    };
}

async function submitQuizData() {
    try {
        const totalQuestions = quizData.length;
        const response = await fetch('/quiz/submit', {
                                       method: 'POST',
                                       headers: {
                                         'Content-Type': 'application/json',
                                         [headerName]: token,
                                       },
                                       credentials: "include",
                                       body: JSON.stringify({ score, totalQuestions })
                                     });
        quizData = await response.json();
    } catch (error) {
        console.error('Error fetching quiz data:', error);
    }
}

function initQuiz() {
    loadQuestion();

    nextButton.addEventListener('click', () => {
        currentQuestion++;

        if (currentQuestion < quizData.length) {
            loadQuestion();
            resultContainer.classList.add('hidden');
            optionsContainer.querySelectorAll('.option').forEach(option => {
                option.style.pointerEvents = 'auto';
            });
        } else {
            showFinalResult();
        }
    });
}

function loadQuestion() {
    const currentQuizData = quizData[currentQuestion];
    questionElement.textContent = currentQuizData.quote;
    questionImageElement.src = "http://localhost:9124/images/img.png"

    optionsContainer.innerHTML = '';
    currentQuizData.options.forEach((optionObj, index) => {
        const optionElement = document.createElement('div');
        optionElement.classList.add('option');
        optionElement.textContent = optionObj.optionLabel;
        optionElement.dataset.answer = optionObj.isCorrectAnswer;
        optionElement.dataset.index = index+1;
        optionElement.addEventListener('click', selectOption);
        optionsContainer.appendChild(optionElement);
    });

    updateProgressBar();
}

function selectOption(e) {
    if (quizEnded) return;

    const selectedOption = e.target;
    const answer = selectedOption.dataset.answer;
    const currentQuizData = quizData[currentQuestion];
    let isCorrect = answer.toLowerCase()==="true";

    if (isCorrect) {
        score++;
        scoreElement.textContent = score;
    }

    selectedOption.classList.add(isCorrect ? 'correct' : 'incorrect');
    resultText.textContent = isCorrect ? 'Correct! 🎉' : 'Incorrect! 😕';
    funFactElement.textContent = currentQuizData.funFact;
    resultContainer.classList.remove('hidden');

    optionsContainer.querySelectorAll('.option').forEach(option => {
        option.style.pointerEvents = 'none';
    });
}

function updateProgressBar() {
    const progress = (currentQuestion / quizData.length) * 100;
    const progressBar = document.getElementById("progress-bar");
      progressBar.style.width = `${progress}%`;

      // Add animation effect
      progressBar.classList.add("animated");

      setTimeout(() => {
        progressBar.classList.remove("animated");
      }, 500);
}

function showFinalResult() {
    quizEnded = true;
    questionElement.textContent = 'Quiz Completed!';
    questionImageElement.style.display = 'none';
    optionsContainer.style.display = 'none';
    resultText.textContent = `Your Score: ${score}/${quizData.length}`;
    funFactElement.textContent = score === quizData.length ? "Perfect score! You're an expert! 🏆" : "Great effort! Try again!";
    submitQuizData();
    nextButton.textContent = 'Restart Quiz';
    nextButton.addEventListener('click', restartQuiz, { once: true });
    resultContainer.classList.remove('hidden');
    updateProgressBar();
}

function restartQuiz() {
    currentQuestion = 0;
    score = 0;
    quizEnded = false;
    scoreElement.textContent = score;
    questionImageElement.style.display = 'block';
    optionsContainer.style.display = 'grid';
    nextButton.textContent = 'Next Question';
    loadQuestion();
    resultContainer.classList.add('hidden');
}

document.addEventListener('DOMContentLoaded', fetchQuizData);
