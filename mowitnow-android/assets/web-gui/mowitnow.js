var SIZE_PX = 34;
var ANIMATION_TIME = 900;

$(function() {
    android.start();
});

/**
 * Creates a lawn.
 */
function initLawn(width, height) {
    var html = '';
    var lawn = $('#lawn');

    for (var x = height; x >= 0 ; x--) {
	html += '<tr>';
        for (var y = 0; y <= width ; y++) {
	  html +='<td class="lawn-square" id="grass-' + y + '-' + x + '"></td>';
	}
	html += '</tr>';
    }
    lawn.append(html);
}

/**
 * Places the mower in the lawn.
 * Rotation is either 0 (north) - 90 (east) - 180 (south) or 270 (west)
 */
function initMower(x, y, rotation) {
  var mower = $('#mower');
  mower.show();
  mower.css('left', (x * SIZE_PX) + 'px');
  mower.css('top', (-(y + 1) * SIZE_PX) + 'px');
  mower.rotate(rotation);
  callback();
}

/**
 * Rotates the mower.
 * Angle should be either 90 or -90.
 */
function rotateMower(angle) {
  var mower = $('#mower');
  var newAngle = Number(mower.getRotateAngle()) + Number(angle);

  mower.rotate({
    duration: ANIMATION_TIME,
    animateTo: newAngle,
    callback: callback
  });
}

/**
 * Moves the mower from its current point (x,y) to a new point (x,y).
 */
function moveMower(curX, curY, newX, newY) {
  var leftIncr = (newX - curX) * SIZE_PX;
  var bottomIncr = (newY - curY) * SIZE_PX;

  // pretend the garden was cut
  $('#grass-' + curX + '-' + curY).animate({
    opacity: '0.80'
  }, ANIMATION_TIME);

  // moves the mower
  $('#mower').animate({
    marginLeft: '+=' + leftIncr + 'px',
    top: '-=' + bottomIncr + 'px',
  }, ANIMATION_TIME, callback);
}

/**
 * Updates the result (location of the mower).
 */
function appendResult(result) {
  $('#result').append(result + '<br />');
}

/**
 * Called when animation is over
 */
function callback() {
    android.processNextInstruction();
}
