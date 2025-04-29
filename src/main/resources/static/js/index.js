// Initialize AOS animation library
document.addEventListener('DOMContentLoaded', function() {
  AOS.init({
    duration: 800,
    easing: 'ease-out',
    once: true
  });

  // Navbar scroll effect
  const navbar = document.querySelector('.navbar');
  window.addEventListener('scroll', () => {
    if (window.scrollY > 50) {
      navbar.classList.add('shadow');
    } else {
      navbar.classList.remove('shadow');
    }
  });

  // Back to top button
  const backToTopButton = document.getElementById('backToTop');
  window.addEventListener('scroll', () => {
    if (window.scrollY > 300) {
      backToTopButton.classList.add('show');
    } else {
      backToTopButton.classList.remove('show');
    }
  });

  backToTopButton.addEventListener('click', () => {
    window.scrollTo({
      top: 0,
      behavior: 'smooth'
    });
  });

  // Smooth scrolling for navigation links
  document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function(e) {
      e.preventDefault();

      const targetId = this.getAttribute('href');
      if (targetId === '#') return;

      const targetElement = document.querySelector(targetId);
      if (targetElement) {
        window.scrollTo({
          top: targetElement.offsetTop - 80,
          behavior: 'smooth'
        });

        // Close mobile menu if open
        const navbarCollapse = document.querySelector('.navbar-collapse');
        if (navbarCollapse.classList.contains('show')) {
          navbarCollapse.classList.remove('show');
        }
      }
    });
  });

  // Sample quiz question functionality
  const checkAnswerButton = document.querySelector('.check-answer');
  if (checkAnswerButton) {
    checkAnswerButton.addEventListener('click', () => {
      const selectedOption = document.querySelector('input[name="sample"]:checked');
      const feedbackElement = document.querySelector('.answer-feedback');
      const correctFeedback = document.querySelector('.correct-answer');
      const wrongFeedback = document.querySelector('.wrong-answer');

      feedbackElement.classList.remove('d-none');

      if (selectedOption && selectedOption.id === 'opt1') {
        // Correct answer
        correctFeedback.classList.remove('d-none');
        wrongFeedback.classList.add('d-none');
      } else {
        // Wrong answer
        correctFeedback.classList.add('d-none');
        wrongFeedback.classList.remove('d-none');
      }

      // Animate the feedback
      feedbackElement.style.animation = 'none';
      setTimeout(() => {
        feedbackElement.style.animation = 'fadeIn 0.5s';
      }, 10);
    });
  }

  // Add hover effect to quiz options
  const optionLabels = document.querySelectorAll('.option-label');
  optionLabels.forEach(label => {
    label.addEventListener('mouseenter', () => {
      label.style.transform = 'translateY(-3px)';
    });

    label.addEventListener('mouseleave', () => {
      label.style.transform = 'translateY(0)';
    });
  });

  // Preload images for better performance
  const preloadImages = () => {
    const images = document.querySelectorAll('img');
    images.forEach(img => {
      const src = img.getAttribute('src');
      if (src) {
        const newImg = new Image();
        newImg.src = src;
      }
    });
  };

  // Run preload after page load
  window.addEventListener('load', preloadImages);

  // Add typing animation to hero heading
  const animateHeroHeading = () => {
    const heroHeading = document.querySelector('.hero-section h1');
    if (heroHeading) {
      heroHeading.style.opacity = '0';
      setTimeout(() => {
        heroHeading.style.transition = 'opacity 1s ease';
        heroHeading.style.opacity = '1';
      }, 300);
    }
  };

  // Run animations
  animateHeroHeading();
});

function startQuiz() {
    window.location.href = "/quiz";
}

function handleLogout() {
    if (confirm('Are you sure you want to logout?')) {
        document.getElementById('logoutForm').submit();
    };
}