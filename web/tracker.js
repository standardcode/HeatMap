var BASE = root + '/heatmap/';
var URL = BASE + 'save';
var mt = mt || [];
var trackId = 0;

var _hm = _hm || {};
var shift = 0;

var previous = [];

function updateShift() {
    _hm.align = _hm.align || 'center';
    _hm.contentWidth = _hm.contentWidth || $(window).width;
    switch(_hm.align) {
        case 'center':
            shift = Math.max(Math.round(($(window).width()-_hm.contentWidth())/2),0);
            break;
        case 'right':
            shift = Math.max($(window).width()-_hm.contentWidth(),0);
            break;
        default:
            shift = 0;
    }
    if(top != window) {
        previewUpdate();
    }
}

function send(s) {
    var img = document.createElement('img');
    img.src = URL + '?' + s;
}

function save() {
    previous = [];
    s = [];
    for(i = 0; i < mt.length; i++) {
        m = mt[i] || [];
        s[i] = 'px' + i + '=' + m.join(',');
    }
    send(s.join('&'));
    mt = [];
}

function add(e, id, max) {
    m = mt[id] || [];
    var x = e.pageX-shift;
    if(x < 0) return;
    var now = +new Date();
    var time = now - (previous[id] || 0);
    previous[id] = now;
    m.push([x, e.pageY, time]);
    if(m.length > max) {
        save();
    } else {
        mt[id] = m;
    }
}

function previewUpdate() {
    if(document.getElementById('_hm')){
        document.getElementsByTagName('body').item(0).removeChild(document.getElementById('_hm'));
    }
    var url =  BASE + 'tracker.html' + '#' + shift + '&' + window.location.href;
    var iframe = document.createElement('iframe');
    iframe.src = url;
    iframe.id = '_hm';
    document.getElementsByTagName('body').item(0).appendChild(iframe);
    $("#_hm").width("0px").height("0px");
}

function track() {
    trackId = $.cookie('hm');
    if(!trackId) {
        trackId = "" + (0xffffffffffffffff * Math.random() + 0xffffff * Math.random());
        $.cookie('hm', trackId, {
            expires: 30, 
            path: '/'
        });
    }
    $(window).resize(updateShift);
    $(window).unload(function(){
        save();
    });
    $(this).mousemove(function(e){
        add(e, 0, 100);
    });
    $(this).bind('touchstart mousedown', function(e){
        add(e, 1, 5);
    });
    $(this).bind('mouseup', function(e){
        add(e, 2, 5);
    });
}

$(document).ready(function(){
    updateShift();
    if(top == window) {//track
        track();
    } else {//preview
        previewUpdate();
    }
});
