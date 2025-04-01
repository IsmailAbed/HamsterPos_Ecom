//add interactivity and animations to a signup form when the page loads and when the user clicks
//the signup button.

// code runs inside the func after the html is loaded
document.addEventListener('DOMContentLoaded', function () {
    const signupForm = document.querySelector('section');//form
    signupForm.style.opacity = 0;

    setTimeout(() => {
      signupForm.style.transition = 'opacity 1s ease-in-out';
      signupForm.style.opacity = 1; // fully visible
    }, 500);

    const signupButton = document.querySelector('button');
    signupButton.addEventListener('click', function () {
      const emailInput = document.querySelector('input[type="email"]');
      const passwordInput = document.querySelector('input[type="password"]');
      const confirmPasswordInput = document.querySelector('input[type="password"][name="confirm-password"]');

      // Check for a valid email and password (you can add your validation logic here)
      const isValid = emailInput.checkValidity() && passwordInput.checkValidity() && confirmPasswordInput.checkValidity();

      if (!isValid) {
        signupForm.classList.add('shake');

        setTimeout(() => {
          signupForm.classList.remove('shake');
        }, 1000);
      }
    });
  });
