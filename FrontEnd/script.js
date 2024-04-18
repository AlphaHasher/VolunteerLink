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

// Data Fetching and Handling
function fetchData() {
    // Simulate a fetch operation with a delay
    return new Promise((resolve, reject) => {
        setTimeout(() => resolve({ data: "Simulated fetch data" }), 2000);
    });
}

// Handle data fetching on button click
const fetchDataButton = document.querySelector('#fetchDataButton');
if (fetchDataButton) {
    fetchDataButton.addEventListener('click', async function () {
        console.log('Fetching data...');
        try {
            const data = await fetchData();
            console.log('Data fetched:', data);
            document.querySelector('#fetchResult').textContent = JSON.stringify(data, null, 2);
        } catch (error) {
            console.error('Failed to fetch data:', error);
            alert('Failed to fetch data.');
        }
    });
}
// Slider Interaction
const slider = document.querySelector('#mySlider');
if (slider) {
    slider.addEventListener('input', function () {
        const valueDisplay = document.querySelector('#sliderValue');
        valueDisplay.textContent = this.value;
    });
}

// Popup Close Functionality
const popupCloseButtons = document.querySelectorAll('.popup-close');
popupCloseButtons.forEach(button => {
    button.addEventListener('click', function () {
        this.parentElement.style.display = 'none';
    });
});