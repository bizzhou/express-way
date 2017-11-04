
var gulp = require('gulp'),
sass = require('gulp-ruby-sass'),
sourcemaps = require('gulp-sourcemaps'),
webserver = require('gulp-webserver');

gulp.task('sass', function () {
    return sass('../sass/style.scss', {
      sourcemap: true,
      style: 'expanded'
    })
    .on('error', function (err) {
        console.error('Error!', err.message);
    })
    .pipe(sourcemaps.write())
    .pipe(gulp.dest('../css/'));
});

gulp.task('watch', function() {
  gulp.watch(['../sass/**/*'], ['sass']);
});

gulp.task('webserver', function() {
    gulp.src('../')
        .pipe(webserver({
            livereload: true,
            open: true
        }));
});

gulp.task('default', ['sass', 'watch', 'webserver']);
