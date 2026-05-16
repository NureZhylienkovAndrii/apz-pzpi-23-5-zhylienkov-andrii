using AutoRent.Data;
using AutoRent.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;
using System.Text;
using System.Text.Json;

namespace AutoRent.Pages.Admin
{
    public class CarsModel : PageModel
    {
        private readonly AppDbContext _db;

        public CarsModel(AppDbContext db)
        {
            _db = db;
        }

        public List<Car> Cars { get; set; }

        [BindProperty]
        public int DeleteId { get; set; }

        [BindProperty]
        public int CarId { get; set; }

        [BindProperty]
        public int NewStatusId { get; set; }

        public async Task OnGetAsync()
        {
            Cars = await _db.Cars.Include(c => c.Status).ToListAsync();
        }

        public async Task<IActionResult> OnPostDeleteAsync()
        {
            var car = await _db.Cars.FindAsync(DeleteId);
            if (car != null)
            {
                _db.Cars.Remove(car);
                await _db.SaveChangesAsync();
            }
            return RedirectToPage();
        }

        public async Task<IActionResult> OnPostChangeStatusAsync()
        {
            var car = await _db.Cars.FindAsync(CarId);
            if (car != null)
            {
                car.StatusId = NewStatusId;
                await _db.SaveChangesAsync();
            }
            return RedirectToPage();
        }

        public FileResult OnGetBackup()
        {
            var backup = new
            {
                Cars = _db.Cars.ToList(),
                Bookings = _db.Bookings.ToList(),
                Users = _db.Users.ToList()
            };

            var json =
                JsonSerializer.Serialize(
                    backup,
                    new JsonSerializerOptions
                    {
                        WriteIndented = true
                    });

            var bytes =
                Encoding.UTF8.GetBytes(json);

            return File(
                bytes,
                "application/json",
                "backup.json");
        }
    }
}
