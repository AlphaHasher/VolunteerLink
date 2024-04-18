document.addEventListener('DOMContentLoaded', function () {
    // Select all sections and navigation buttons
    const sections = document.querySelectorAll('section');
    const navButtons = document.querySelectorAll('.nav-button');

    // Function to switch visibility of sections
    function switchSection(activeButton) {
        sections.forEach(section => {
            section.style.display = 'none'; // Hide all sections
        });
        const target = activeButton.getAttribute('data-target');
        const targetSection = document.querySelector(target);
        if (targetSection) {
            targetSection.style.display = 'block'; // Show the target section
        } else {
            console.warn('Target section not found:', target);
        }
    }
    // Attach event listeners to navigation buttons
    navButtons.forEach(button => {
        button.addEventListener('click', function () {
            navButtons.forEach(btn => btn.classList.remove('active'));
            this.classList.add('active');
            switchSection(this);
        });
    });
});

//Validation and Submission
function validateForm() {
    let isValid = true;
    const inputs = document.querySelectorAll('#myForm input[required]');

    // Validate each required input
    inputs.forEach(input => {
        if (!input.value.trim()) {
            console.error(`Validation failed: ${input.name} is required.`);
            input.classList.add('error');
            isValid = false;
        } else {
            input.classList.remove('error');
        }
    });

    return isValid;
}
// Handle form submission
const form = document.querySelector('#myForm');
if (form) {
    form.addEventListener('submit', function (event) {
        event.preventDefault();
        if (validateForm()) {
            const formData = new FormData(this);
            console.log('Form Data:', Object.fromEntries(formData));
            alert('Form submitted successfully!');
        } else {
            alert('Please fill in all required fields.');
        }
    });
}

