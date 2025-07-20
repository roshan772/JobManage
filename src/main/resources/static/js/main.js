// In js/main.js
let currentPage = 0;
let pageSize = 10;
let totalPages = 0;

$(document).ready(function () {
    loadJobs(currentPage);

    $('#nextPage').click(() => {
        if (currentPage < totalPages - 1) {
            currentPage++;
            loadJobs(currentPage);
        }
    });

    $('#prevPage').click(() => {
        if (currentPage > 0) {
            currentPage--;
            loadJobs(currentPage);
        }
    });

    $('#firstPage').click(() => {
        currentPage = 0;
        loadJobs(currentPage);
    });

    $('#lastPage').click(() => {
        currentPage = totalPages - 1;
        loadJobs(currentPage);
    });
});

function loadJobs(page) {
    $.ajax({
        url: `/api/v1/job/getAll?page=${page}&size=${pageSize}`,
        type: 'GET',
        success: function (response) {
            totalPages = response.totalPages;
            $('#paginationInfo').text(`Page ${page + 1} of ${totalPages}`);

            let rows = '';
            response.content.forEach((job, index) => {
                rows += `
                    <tr>
                        <td>${page * pageSize + index + 1}</td>
                        <td>${job.jobTitle}</td>
                        <td>${job.company}</td>
                        <td>${job.location}</td>
                        <td>${job.type}</td>
                        <td>${job.status}</td>
                        <td>
                            <button class="btn btn-sm btn-warning" onclick="editJob(${job.id})">Edit</button>
                            <button class="btn btn-sm btn-danger" onclick="deleteJob(${job.id})">Delete</button>
                        </td>
                    </tr>
                `;
            });

            $('#jobsTableBody').html(rows);
        }
    });
}


$('#addJobForm').on('submit', function (e) {
    e.preventDefault(); // Prevent actual form submission

    const jobData = {
        jobTitle: $('#jobTitle').val(),
        company: $('#companyName').val(),
        location: $('#jobLocation').val(),
        type: $('#jobType').val(),
        jobDescription: $('#jobDescription').val(),
        status: 'Active'
    };

    $.ajax({
        url: '/api/v1/job/create',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(jobData),
        success: function () {
            alert('Job added successfully!');
            $('#addJobForm')[0].reset();
            $('#addJobModal').modal('hide');
            loadJobs(currentPage);
        },
        error: function (err) {
            alert('Error adding job!');
            console.error(err);
        }
    });
});


function deleteJob(id) {
    if (confirm('Are you sure you want to delete this job?')) {
        $.ajax({

            url: `/api/v1/job/delete/${id}`,
            type: 'DELETE',
            success: function () {
                alert('Job deleted successfully!');
                loadJobs(currentPage);
            },
            error: function (err) {
                alert('Error deleting job!');
                console.error(err);
            }
        });
    }
}

function editJob(id) {
    $.ajax({
        url: `/api/v1/job/${id}`,
        type: 'GET',
        success: function (job) {
            $('#editJobId').val(job.id);
            $('#editJobTitle').val(job.jobTitle);
            $('#editCompanyName').val(job.company);
            $('#editJobLocation').val(job.location);
            $('#editJobType').val(job.type);
            $('#editJobDescription').val(job.jobDescription);
            $('#editJobModal').modal('show');
        },
        error: function (err) {
            alert('Error loading job details!');
            console.error(err);
        }
    });
}


$('#editJobForm').on('submit', function (e) {
    e.preventDefault();

    const jobData = {
        id: parseInt($('#editJobId').val()),
        jobTitle: $('#editJobTitle').val(),
        company: $('#editCompanyName').val(),
        location: $('#editJobLocation').val(),
        type: $('#editJobType').val(),
        jobDescription: $('#editJobDescription').val(),
        status: 'Active'
    };

    $.ajax({
        url: `/api/v1/job/edit`,
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(jobData),
        success: function () {
            alert('Job updated successfully!');
            $('#editJobForm')[0].reset();
            $('#editJobModal').modal('hide');

            loadJobs(currentPage);
        },
        error: function (err) {
            alert('Error updating job!');
            console.error(err);
        }
    });
});


$('#searchInput').on('keyup', function () {
    const searchTerm = $(this).val().toLowerCase();
    $('#jobsTableBody tr').each(function () {
        const rowText = $(this).text().toLowerCase();
        $(this).toggle(rowText.includes(searchTerm));
    });
});

