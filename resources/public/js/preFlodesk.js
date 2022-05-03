(function(w, d, t, h, s, n) {
  w.FlodeskObject = n;
  var fn = function() {
    (w[n].q = w[n].q || []).push(arguments);
  };
  w[n] = w[n] || fn;
})(window, document, 'script', 'https://assets.flodesk.com', '/universal', 'fd');

// (function() {
//   window.FlodeskObject = 'fd';
//   var fn = function() {
//     (window['fd'].q = window['fd'].q || []).push(arguments);
//   };
//   window['fd'] = window['fd'] || fn;
// })();
