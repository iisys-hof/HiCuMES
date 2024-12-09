using System;
using System.Collections.Generic;
using System.IO;
using System.Text.Json.Serialization;
using System.Threading;
using System.Threading.Tasks;
using HiCuMes.Persistence.Context;
using HiCuMes.Persistence.DataAccessRepository;
using HiCuMes.Persistence.DataAccessRepository.Implementation;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.StaticFiles;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.FileProviders;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using Microsoft.Net.Http.Headers;
using Serilog;

namespace Api;

public class Program
{
  public static void Main(string[] args)
  {
    var builder = WebApplication.CreateBuilder(args);
    if (OperatingSystem.IsWindows())
    {
      builder.Services.AddWindowsService();
    }

    builder.Logging.ClearProviders();

    Log.Logger = new LoggerConfiguration()
      .ReadFrom.Configuration(builder.Configuration)
      .CreateLogger();

    //Add support to logging with SERILOG
    builder.Logging.AddSerilog(Log.Logger, true);
    builder.Host.UseSerilog(Log.Logger, true);

    // Add services to the container.
    // builder.Services.AddAuthorization();

    //AddScoped ist besser
    builder.Services.AddSingleton(typeof(IReadRepository<>), typeof(DefaultReadRepository<>));
    builder.Services.AddSingleton(typeof(IWriteRepository<>), typeof(DefaultWriteRepository<>));

    builder.Services.AddControllers().AddJsonOptions(options => options.JsonSerializerOptions.ReferenceHandler = ReferenceHandler.IgnoreCycles);
    // Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
    builder.Services.AddEndpointsApiExplorer();
    builder.Services.AddSwaggerGen();

    // builder.Services.AddMvc();



    var connectionString = builder.Configuration.GetConnectionString("DefaultConnection");
    builder.Services.AddDbContext<HiCuMESDbContext>(x =>
    {
      x.UseMySql(connectionString, MySqlServerVersion.LatestSupportedServerVersion);

#if DEBUG
      x.EnableDetailedErrors();
      x.EnableSensitiveDataLogging();
#endif
    });

    builder.Services.AddHostedService<OnStartup>();

    var app = builder.Build();

    var clientPath = Path.Combine(AppContext.BaseDirectory, "apps\\web-app");
    if (!Directory.Exists(clientPath)) Directory.CreateDirectory(clientPath);
    var fileProvider = new PhysicalFileProvider(clientPath);
    var options = new DefaultFilesOptions
    {
      RequestPath = "",
      FileProvider = fileProvider,
      DefaultFileNames = new List<string> { "index.html" }
    };

    app.UseDefaultFiles(options);

    var contentTypeProvider = new FileExtensionContentTypeProvider();
    contentTypeProvider.Mappings[".webmanifest"] = "application/manifest+json";
    contentTypeProvider.Mappings[".md"] = "text/markdown";

    var staticFileOptions = new StaticFileOptions()
    {
      ContentTypeProvider = contentTypeProvider,
      OnPrepareResponse = ctx =>
      {
        // set cache-control header to 1 year or
        // cache-control: public, max-age=31536000
        const int durationInSeconds = 60 * 60 * 24 * 365;
        ctx.Context.Response.Headers[HeaderNames.CacheControl] = "public,max-age=" + durationInSeconds;
      },
      FileProvider = fileProvider,
    };

    app.UseStaticFiles(staticFileOptions);

    app.UseRouting();
   app.UseCors(
      corsPolicyBuilder => corsPolicyBuilder
        .AllowAnyMethod()
        .AllowAnyHeader()
        .SetIsOriginAllowed(x => true)
        .AllowCredentials()
    );
    app.UseEndpoints(_ => {});

    // Configure the HTTP request pipeline.
    if (app.Environment.IsDevelopment())
    {
      app.UseSwagger();
      app.UseSwaggerUI(c =>
      {
        c.SwaggerEndpoint("/swagger/v1/swagger.json", "HiCuMES API V1");
        c.RoutePrefix = "swagger";
      });
    }
    else
    {
      app.UseExceptionHandler("/Error");
      // The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
      app.UseHsts();
    }

    app.UseHttpsRedirection();

    app.UseSerilogRequestLogging();

    app.MapSwagger();
    app.MapControllers();

    app.UseSpa(spa =>
    {
      spa.Options.DefaultPageStaticFileOptions = new StaticFileOptions()
      {
        FileProvider = fileProvider,
      };
    });

    //app.UseAuthorization();
    // app.UseEndpoints(endpoints =>
    // {
    //   endpoints.MapControllers();
    // });

    app.Run();
  }
}

file class OnStartup(IServiceScopeFactory serviceScopeFactory, ILogger<OnStartup> logger) : IHostedService
{

  private readonly ILogger<OnStartup> _logger = logger;
  public async Task StartAsync(CancellationToken cancellationToken)
  {
    var scope = serviceScopeFactory.CreateAsyncScope();
    await using (scope.ConfigureAwait(false))
    {
      var db = scope.ServiceProvider.GetRequiredService<HiCuMESDbContext>();
      _logger.LogInformation("Ensure the database exists");
      await db.Database.EnsureCreatedAsync(cancellationToken).ConfigureAwait(false);
    }
  }


  public Task StopAsync(CancellationToken cancellationToken) => Task.CompletedTask;
}
