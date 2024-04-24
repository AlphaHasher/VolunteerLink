document.addEventListener('DOMContentLoaded', function () {
    // Select all sections and navigation buttons
    const event = [
        {date: 'Apr 3, 9:30 - 10:30am', day: 'Wednesday', description: 'Food Drive: Delivering food to those in need', id: 1 },
        { date: 'Apr 3, 10am - 1pm', day: 'Wednesday', description: 'Creek Clean Up: Help clean up the trash at American River', id: 2 },
        { date: 'Apr 6, 12pm - 4pm', day: 'Saturday', description: 'Park Clean Up: Help clean up local parks near Sac State', id: 3 }
    ];
    
    function renderEvents() {
        const eventsContainer = document.querySelector('.info-bar-group');
        eventsContainer.innerHTML = ''; // Clear existing content
        events.forEach(event => {
            const eventElement = document.createElement('div');
            eventElement.className = 'event-item';
            eventElement.innerHTML = `
                <div class="event-date">${event.date}</div>
                <div class="event-day">${event.day}</div>
                <div class="event-description">${event.description}</div>
                <button class="accept" onclick="handleAccept(${event.id})">Accept</button>
                <button class="decline" onclick="handleDecline(${event.id})">Decline</button>
            `;
            eventsContainer.appendChild(eventElement);
        });
    }
    window.handleAccept = function(eventId) {
        updateEventStatus(eventId, 'accept');
    };

    window.handleDecline = function(eventId) {
        updateEventStatus(eventId, 'decline');
    };
    function updateEventStatus(eventId, action) {
        // Simulating a fetch request to the server
        fetch(`https://example.com/api/events/${eventId}/${action}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ status: action })
        })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            alert(`You have ${action}ed the event.`);
            // Optionally, you can remove or update the event item in the DOM here.
        })
        .catch((error) => {
            console.error('Error:', error);
            alert('Failed to update the event status. Please try again.');
        });
    }

    renderEvents();
});

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