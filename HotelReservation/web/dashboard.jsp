<!-- Hero Section -->
<section class="site-hero inner-page overlay" style="background-image: url(images/hero_4.jpg)" data-stellar-background-ratio="0.5">
    <div class="container">
        <div class="row site-hero-inner justify-content-center align-items-center">
            <div class="col-md-10 text-center" data-aos="fade">
                <h1 class="heading mb-3">User  Profile</h1>
                <ul class="custom-breadcrumbs mb-4">
                    <li><a href="index.jsp">Home</a></li>
                    <li>&bullet;</li>
                    <li>User Profile</li>
                </ul>
            </div>
        </div>
    </div>
</section>
<!-- END Hero Section -->

<!-- User Information Section -->
<section class="section contact-section">
    <div class="container">
        <div class="row">
            <div class="col-md-7" data-aos="fade-up" data-aos-delay="100">
                <form action="profile.jsp#" method="post" class="bg-white p-md-5 p-4 mb-5 border">
                    <div class="row">
                        <div class="col-md-6 form-group">
                            <label for="name">Name</label>
                            <input type="text" id="name" class="form-control" value="[User   Name]">
                        </div>
                        <div class="col-md-6 form-group">
                            <label for="phone">Phone</label>
                            <input type="text" id="phone" class="form-control" value="[User   Phone]">
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 form-group">
                            <label for="email">Email</label>
                            <input type="email" id="email" class="form-control" value="[User   Email]">
                        </div>
                    </div>
                    <div class="row mb-4">
                        <div class="col-md-12 form-group">
                            <label for="message">Write Message</label>
                            <textarea name="message" id="message" class="form-control" cols="30" rows="8"></textarea>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 form-group">
                            <input type="submit" value="Update Profile" class="btn btn-primary text-white font-weight-bold">
                        </div>
                    </div>
                </form>
            </div>

            <div class="col-md-5" data-aos="fade-up" data-aos-delay="200">
                <div class="row">
                    <div class="col-md-10 ml-auto contact-info">
                        <p><span class="d-block">Address:</span> <span>98 West 21th Street, Suite 721 New York NY 10016</span></p>
                        <p><span class="d-block">Phone:</span> <span>(+1) 234 4567 8910</span></p>
                        <p><span class="d-block">Email:</span> <span><a href="mailto:[User   Email]">[User   Email]</a></span></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>