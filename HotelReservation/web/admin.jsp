<!-- Hero Section -->
<section class="site-hero inner-page overlay" style="background-image: url(images/hero_4.jpg)" data-stellar-background-ratio="0.5">
    <div class="container">
        <div class="row site-hero-inner justify-content-center align-items-center">
            <div class="col-md-10 text-center" data-aos="fade">
                <h1 class="heading mb-3">Admin Dashboard</h1>
                <ul class="custom-breadcrumbs mb-4">
                    <li><a href="index.jsp">Home</a></li>
                    <li>&bullet;</li>
                    <li>Admin Dashboard</li>
                </ul>
            </div>
        </div>
    </div>
</section>
<!-- END Hero Section -->

<!-- Reservations Management Section -->
<section class="section contact-section">
    <div class="container">
        <div class="row">
            <div class="col-md-12" data-aos="fade-up" data-aos-delay="100">
                <h2 class="mb-4">Manage Hotel Reservations</h2>
                <button class="btn btn-primary" data-toggle="modal" data-target="#addReservationModal">Add Reservation</button>
                <table class="table table-bordered mt-4">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Check-in Date</th>
                            <th>Check-out Date</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Data reservations will be populated here from the database -->
                        <tr>
                            <td>1</td>
                            <td>John Doe</td>
                            <td>john@example.com</td>
                            <td>2023-10-01</td>
                            <td>2023-10-05</td>
                            <td>
                                <button class="btn btn-warning" data-toggle="modal" data-target="#editReservationModal">Edit</button>
                                <button class="btn btn-danger" onclick="deleteReservation(1)">Delete</button>
                            </td>
                        </tr>
                        <!-- Repeat for other reservations -->
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</section>

<!-- Add Reservation Modal -->
<div class="modal fade" id="addReservationModal" tabindex="-1" role="dialog" aria-labelledby="addReservationModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addReservationModalLabel">Add Reservation</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="addReservationForm">
                    <div class="form-group">
                        <label for="name">Name</label>
                        <input type="text" id="name" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" id="email" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label for="checkin">Check-in Date</label>
                        <input type="date" id="checkin" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label for="checkout">Check-out Date</label>
                        <input type="date" id="checkout" class="form-control" required>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" onclick="addReservation()">Save Reservation</button>
            </div>
        </div>
    </div>
</div>

<!-- Edit Reservation Modal -->
<div class="modal fade" id="editReservationModal" tabindex="-1" role="dialog" aria-labelledby="editReservationModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editReservationModalLabel">Edit Reservation</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="editReservationForm">
                    <div class="form-group">
                        <label for="editName">Name</label>
                        <input type="text" id="editName" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label for="editEmail">Email</label>
                        <input type="email" id="editEmail" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label for="editCheckin">Check-in Date</label>
                        <input type="date" id="editCheckin" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label for="editCheckout">Check-out Date</label>
                        <input type="date" id="editCheckout" class="form-control" required>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" onclick="updateReservation()">Update Reservation</button>
            </div>
        </div>
    </div>
</div>

<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script>
                    function addReservation() {
                        // Logic to add reservation
                        alert("Reservation added!");
                        $('#addReservationModal').modal('hide');
                    }

                    function updateReservation() {
                        // Logic to update reservation
                        alert("Reservation updated!");
                        $('#editReservationModal').modal('hide');
                    }

                    function deleteReservation(id) {
                        // Logic to delete reservation
                        alert("Reservation with ID " + id + " deleted!");
                    }
</script>