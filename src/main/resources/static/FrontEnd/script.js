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


document.addEventListener('DOMContentLoaded', function() {
    const roleContainer = document.getElementById('roleContainer');

    window.addRole = function() {
        const newRoleDiv = document.createElement('div');
        newRoleDiv.className = 'form-group role';
        newRoleDiv.innerHTML = `
            <input type="text" name="roleNames" placeholder="Role Name" required>
            <textarea name="roleDescriptions" placeholder="Role Description" required></textarea>
            <input type="number" name="numbersNeeded" placeholder="Number of Volunteers" required min="1">
            <button type="button" class="btn btn-danger" onclick="removeRole(this)">Delete Role</button>
        `;
        roleContainer.appendChild(newRoleDiv);
    };

    window.removeRole = function(button) {
        button.parentNode.remove();
    };

    // Initialize with one role input
    addRole();
});

window.populateRandomData = function() {
    const eventNames = ["Community Cleanup", "Charity Run", "Book Donation Drive", "Blood Drive", "Food Bank Fundraiser", "Pet Adoption Day", "Recycling Program", "Soup Kitchen Volunteer"];
    const descriptions = ["Help clean up the local park.", "Participate in a 5k run for charity.", "Donate books for local schools.", "Donate blood to save lives.", "Help sort and pack food donations.", "Raise funds for a good cause.", "Adopt a pet from a local shelter.", "Recycle to protect the environment.", "Serve meals to the homeless."];
    const locations = ["Central Park", "Riverbank Plaza", "Downtown Library", "Community Center", "Red Cross Center", "City Hall", "Animal Shelter", "Recycling Center", "Homeless Shelter"];
    const tags = ["Community", "Charity", "Donation", "Health", "Food", "Fundraiser", "Pets", "Environment", "Volunteering"];

    // Set random values for standard fields
    document.getElementById('eventName').value = eventNames[Math.floor(Math.random() * eventNames.length)];
    document.getElementById('description').value = descriptions[Math.floor(Math.random() * descriptions.length)];
    document.getElementById('location').value = locations[Math.floor(Math.random() * locations.length)];
    document.getElementById('startDate').value = new Date().toISOString().split('T')[0];
    document.getElementById('endDate').value = new Date(new Date().getTime() + (86400000 * 7)).toISOString().split('T')[0]; // 1 week later
    document.getElementById('startTime').value = "09:00";
    document.getElementById('endTime').value = "17:00";

    const tagsContainer = document.getElementById('tagsContainer');
    tagsContainer.innerHTML = ''; // Clear existing tags

    // Generate random tags
    const numberOfTags = Math.floor(Math.random() * (tags.length - 1)) + 1;
    for (let i = 0; i < numberOfTags; i++) {
        const tagIndex = Math.floor(Math.random() * tags.length);
        const tagValue = tags[tagIndex];
        addTag(tagValue);
        tags.splice(tagIndex, 1); // Remove the added tag from the array to avoid duplicates
    }

    setTimeout(() => {
        updateHiddenTags();
    }, 0);

    // Update hidden input for tags
    document.getElementById('hiddenTags').value = Array.from(tagsContainer.children).map(tagSpan => tagSpan.textContent).join(',');

    // Clear existing roles
    document.querySelectorAll('.role').forEach(role => role.remove());

    // Add a few random roles
    const roleNames = ["Volunteer", "Organizer", "Helper", "Coordinator", "Assistant", "Leader", "Supporter", "Team Member", "Facilitator"];
    const roleDescriptions = ["Assist in managing participants", "Lead a team of volunteers", "Help with setup and cleanup"];

    // Generate 3 random roles
    const numRoles = Math.floor(Math.random() * 3) + 1;
    for (let i = 0; i < numRoles; i++) {
        const newRoleDiv = document.createElement('div');
        newRoleDiv.className = 'form-group role';
        newRoleDiv.innerHTML = `
            <input type="text" name="roleNames" placeholder="Role Name" required value="${roleNames[Math.floor(Math.random() * roleNames.length)]}">
            <textarea name="roleDescriptions" placeholder="Role Description" required>${roleDescriptions[Math.floor(Math.random() * roleDescriptions.length)]}</textarea>
            <input type="number" name="numbersNeeded" placeholder="Number of Volunteers" required min="1" value="${Math.floor(Math.random() * 5) + 1}">
            <button type="button" class="btn btn-danger" onclick="removeRole(this)">Delete Role</button>`;
        document.getElementById('roleContainer').appendChild(newRoleDiv);
    }
};

window.addTag = function(tagValue) {
    const tagsContainer = document.getElementById('tagsContainer');

    // Create tag element
    const tagSpan = document.createElement('span');
    tagSpan.className = 'tag-item';

    // Create text node for tag
    const textNode = document.createTextNode(tagValue);
    tagSpan.appendChild(textNode);

    // Create remove button for tag
    const removeBtn = document.createElement('button');
    removeBtn.textContent = 'X';
    removeBtn.className = 'remove-tag-btn';
    removeBtn.addEventListener('click', function() {
        tagsContainer.removeChild(tagSpan);
        updateHiddenTags(); // Ensure this updates correctly without the 'X'
    });

    // Append text node and remove button to the tag span
    tagSpan.appendChild(removeBtn);
    tagsContainer.appendChild(tagSpan);
};


function updateHiddenTags() {
    const tagsContainer = document.getElementById('tagsContainer');
    const tags = Array.from(tagsContainer.querySelectorAll('.tag-item')).map(tagSpan => tagSpan.firstChild.textContent.trim());
    document.getElementById('hiddenTags').value = tags.join(',');
}


document.addEventListener('DOMContentLoaded', function() {
    var tags = [];

    function updateTagsInput() {
        document.getElementById('hiddenTags').value = tags.join(',');
    }

    document.getElementById('addTagBtn').addEventListener('click', function() {
        var tagInput = document.getElementById('tagInput');
        var tagValue = tagInput.value.trim();

        if(tagValue && tags.indexOf(tagValue) === -1) {
            tags.push(tagValue);
            updateTagsInput();

            // Create tag element
            var tagSpan = document.createElement('span');
            tagSpan.textContent = tagValue;
            tagSpan.className = 'tag-item';

            // Create remove button for tag
            var removeBtn = document.createElement('button');
            removeBtn.textContent = 'X';
            removeBtn.addEventListener('click', function() {
                var tagIndex = tags.indexOf(tagValue);
                if(tagIndex !== -1) {
                    tags.splice(tagIndex, 1);
                    updateTagsInput();
                    tagSpan.parentNode.removeChild(tagSpan);
                }
            });

            // Append tag and remove button to the tags container
            tagSpan.appendChild(removeBtn);
            document.getElementById('tagsContainer').appendChild(tagSpan);

            // Clear the input
            tagInput.value = '';
            tagInput.focus();
        }
    });
});