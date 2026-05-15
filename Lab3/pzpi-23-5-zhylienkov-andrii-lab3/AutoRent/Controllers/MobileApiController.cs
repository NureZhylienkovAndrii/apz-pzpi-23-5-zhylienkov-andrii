using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using AutoRent.Data;
using AutoRent.Models;

namespace AutoRent.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class MobileApiController : ControllerBase
    {
        private readonly AppDbContext _context;

        public MobileApiController(AppDbContext context)
        {
            _context = context;
        }

        [HttpGet("cars")]
        public async Task<IActionResult> GetCars()
        {
            var cars = await _context.Cars.ToListAsync();

            return Ok(cars);
        }

        [HttpGet("cars/{id}")]
        public async Task<IActionResult> GetCar(int id)
        {
            var car = await _context.Cars.FindAsync(id);

            if (car == null)
                return NotFound();

            return Ok(car);
        }

        [HttpPost("rent")]
        public async Task<IActionResult> RentCar([FromBody] RentalRequest request)
        {
            var rental = new Booking
            {
                CarId = request.CarId,
                StartDate = request.StartDate,
                EndDate = request.EndDate
            };

            _context.Bookings.Add(rental);

            await _context.SaveChangesAsync();

            return Ok(new
            {
                message = "Rental created"
            });
        }
    }

    public class RentalRequest
    {
        public int CarId { get; set; }

        public DateTime StartDate { get; set; }

        public DateTime EndDate { get; set; }
    }
}