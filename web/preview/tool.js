function resize() {
    $('#tool').width($(window).width());
    $('#page').height($(window).height()-102).width($(window).width());
}

function submit() {
    var url = $('#url').val();
    if (url) {
        window.page.location = url;
        updateHeatmap();
    }
    return false;
}

var lastShift = 0;

function updateShift(shift) {
    lastShift = shift;
    $('#cover img').css('margin-left', shift + 'px');
}

function updateCover(html) {
    $('#cover').html('').html(html);
}

function updateUrl(url) {
    $('#url').val(url);
    window.location.hash = url;
}

function updateHeatmap() {
    if($('#render').is(':checked')) {
        src = root + '/heatmap/image?' + $('#options').serialize() + getTimeScope();
        updateCover('<img src="' + src + '"/>');
        $('#cover').width('100%');
        updateShift(lastShift);
    } else {
        updateCover('');
        $('#cover').width('auto');
    }
}

$(window).resize(resize);

$(document).ready(function(){
    resize();
    $('fieldset').click(function(){
        $(this).children('legend').children('input[type="radio"]').attr('checked','checked');
    });
    $('#options').submit(submit);
    $('#options').change(function(){
        updateHeatmap();
    });
    $(".time").datepicker();
    $("#opacity").slider({
        value:.5,
        min: 0,
        max: 1,
        step: .01,
        change: function(e, ui) { 
            $('#cover').fadeTo("normal", ui.value);
        }
    });
    $('#about').click(function(){
        alert("HeatMap 0.2.0\nCopyright (c) 2011 Łukasz Sutkowski\nLicense GPL 3");
    });
    updateUrl(location.hash.substring(1));
    submit();
});

function getTimeScope() {
    var type = $("input[name='timeType']:checked").attr('id');
    var time = '';
    switch(type) {
        case "dateRange":
            var from = $('#from').datepicker('getDate');
            var to = $('#to').datepicker('getDate');    
            if(from && to) {
                time = '&from=' + from.getTime() + '&to=' + (to.getTime() + 24 * 3600000);
            }
            break;
        case "pointTime":
            var point = $('#point').datepicker('getDate');
            var radius = $('#radius').val();
            if(point && (radius = parseInt(radius))>0) {
                time = '&point=' + point.getTime() + '&radius=' + radius;
            }
            break;
        case "lastItems":
            var last = $('#last').val();
            if((last = parseInt(last))>0) {
                time = '&last=' + last;
            }
            break;
    }
    return time;    
}