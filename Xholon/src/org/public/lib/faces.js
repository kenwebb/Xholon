/**
 * faces.js
 * A JavaScript library for generating vector-based cartoon faces.
 * http://dumbmatter.com/facesjs/http://dumbmatter.com/facesjs/
 * https://github.com/dumbmatter/facesjs
 *
 * adapted by Ken Webb for use in Xholon
 * http://www.primordion.com/Xholon/gwt/index.html
 * ken@primordion.com
 * January 27, 2017
 */
var faces = (function () {
    "use strict";

    var eye = [], eyebrow = [], hair = [], head = [], mouth = [], nose = [];

    function newPath(paper) {
        var e;
        e = document.createElementNS("http://www.w3.org/2000/svg", "path");
        paper.appendChild(e);
        return e;
    }

    function newText(paper) {
        var e;
        e = document.createElementNS("http://www.w3.org/2000/svg", "text");
        paper.appendChild(e);
        return e;
    }

    // Rotate around center of bounding box of element e, like in Raphael.
    function rotateCentered(e, angle) {
        var bbox, cx, cy;

        bbox = e.getBBox();
        cx = bbox.x + bbox.width / 2;
        cy = bbox.y + bbox.height / 2;
        e.setAttribute("transform", "rotate(" + angle + " " + cx + " " + cy + ")");
    }

    // Scale relative to the center of bounding box of element e, like in Raphael.
    // Set x and y to 1 and this does nothing. Higher = bigger, lower = smaller.
    function scaleCentered(e, x, y) {
        var bbox, cx, cy, strokeWidth, tx, ty;

        bbox = e.getBBox();
        cx = bbox.x + bbox.width / 2;
        cy = bbox.y + bbox.height / 2;
        tx = (cx * (1 - x)) / x;
        ty = (cy * (1 - y)) / y;

        e.setAttribute("transform", "scale(" + x + " " + y + "), translate(" + tx + " " + ty + ")");

        // Keep apparent stroke width constant, similar to how Raphael does it (I think)
        strokeWidth = e.getAttribute("stroke-width");
        if (strokeWidth) {
            e.setAttribute("stroke-width", strokeWidth / Math.abs(x));
        }
    }

    // Defines the range of fat/skinny, relative to the original width of the default head.
    function fatScale(fatness) {
        return 0.75 + 0.25 * fatness;
    }

    head.push(function (paper, fatness, color) {
        var e;

        e = newPath(paper);
        e.setAttribute("d", "M 200,100" +
                       "c 0,0 180,-10 180,200" +
                       "c 0,0 0,210 -180,200" +
                       "c 0,0 -180,10 -180,-200" +
                       "c 0,0 0,-210 180,-200");
        e.setAttribute("fill", color);
        scaleCentered(e, fatScale(fatness), 1);
    });

    eyebrow.push(function (paper, lr, cx, cy) {
        // Standard
        var e, x = cx - 30, y = cy;

        e = newPath(paper);
        if (lr === "l") {
            e.setAttribute("d", "M " + x + "," + y +
                           "c 0,0 -3,-30 60,0");
        } else {
            e.setAttribute("d", "M " + x + "," + y +
                           "c 0,0 63,-30 60,0");
        }
        e.setAttribute("stroke", "#000");
        e.setAttribute("stroke-width", "8");
        e.setAttribute("fill", "none");
    });
    eyebrow.push(function (paper, lr, cx, cy) {
        // Standard up (KSW)
        var e, x = cx - 30, y = cy - 15;

        e = newPath(paper);
        if (lr === "l") {
            e.setAttribute("d", "M " + x + "," + y +
                           "c 0,0 -3,-30 60,0");
        } else {
            e.setAttribute("d", "M " + x + "," + y +
                           "c 0,0 63,-30 60,0");
        }
        e.setAttribute("stroke", "#000");
        e.setAttribute("stroke-width", "8");
        e.setAttribute("fill", "none");
    });

    eye.push(function (paper, lr, cx, cy, angle, color) {
        // Horizontal
        var e, x = cx - 30, y = cy;

        e = newPath(paper);
        e.setAttribute("d", "M " + x + "," + y +
                       "h 60");
        e.setAttribute("stroke", "#000");
        e.setAttribute("stroke-width", "8");
        e.setAttribute("fill", "none");
        rotateCentered(e, (lr === "l" ? angle : -angle));
    });
    eye.push(function (paper, lr, cx, cy, angle, color) {
        // Normal (circle with a dot in it)
        var e, x = cx, y = cy + 20;

        e = newPath(paper);
        e.setAttribute("d", "M " + x + "," + y +
                       "a 30,20 0 1 1 0.1,0");
        e.setAttribute("stroke", "#000");
        e.setAttribute("stroke-width", "6");
        e.setAttribute("fill", "#f0f0f0");
        rotateCentered(e, (lr === "l" ? angle : -angle));

        e = newPath(paper);
        e.setAttribute("d", "M " + x + "," + (y - 12) +
                       "a 12,8 0 1 1 0.1,0");
        e.setAttribute("fill", color);
        rotateCentered(e, (lr === "l" ? angle : -angle));
    });
    eye.push(function (paper, lr, cx, cy, angle, color) {
        // Dot
        var e, x = cx, y = cy + 13;

        e = newPath(paper);
        e.setAttribute("d", "M " + x + "," + y +
                       "a 20,15 0 1 1 0.1,0");
        e.setAttribute("fill", color);
        rotateCentered(e, (lr === "l" ? angle : -angle));
    });
    eye.push(function (paper, lr, cx, cy, angle, color) {
        // Arc eyelid
        var e, x = cx, y = cy + 20;

        e = newPath(paper);
        e.setAttribute("d", "M " + x + "," + y +
                       "a 17,17 0 1 1 0.1,0 z");
        e.setAttribute("fill", color);
        rotateCentered(e, (lr === "l" ? angle : -angle));

        e = newPath(paper);
        e.setAttribute("d", "M " + (x - 40) + "," + (y - 14) +
                       "c 36,-44 87,-4 87,-4");
        e.setAttribute("stroke", "#000");
        e.setAttribute("stroke-width", "4");
        e.setAttribute("fill", "none");
        rotateCentered(e, (lr === "l" ? angle : -angle));
    });

    nose.push(function (paper, cx, cy, size, posY, flip) {
        // V
        var e, x = cx - 30, y = cy, scale = size + 0.5;

        e = newPath(paper);
        e.setAttribute("d", "M " + x + "," + y +
                       "l 30,30" +
                       "l 30,-30");
        e.setAttribute("stroke", "#000");
        e.setAttribute("stroke-width", "8");
        e.setAttribute("fill", "none");
        scaleCentered(e, scale, scale);
    });
    nose.push(function (paper, cx, cy, size, posY, flip) {
        // Pinnochio
        var e, x = cx, y = cy - 10, scale = size + 0.5;

        e = newPath(paper);
        e.setAttribute("d", "M " + (flip ? x - 48 : x) + "," + y +
                       "c 0,0 50,-30 0,30");
        e.setAttribute("stroke", "#000");
        e.setAttribute("stroke-width", "8");
        e.setAttribute("fill", "none");
        if (flip) {
            scaleCentered(e, -scale, scale);
        } else {
            scaleCentered(e, scale, scale);
        }
    });
    nose.push(function (paper, cx, cy, size, posY, flip) {
        // Big single
        var e, x = cx - 9, y = cy - 25, scale = size + 0.5;

        e = newPath(paper);
        e.setAttribute("d", "M " + x + "," + y +
                       "c 0,0 -20,60 9,55" +
                       "c 0,0 29,5 9,-55");
        e.setAttribute("stroke", "#000");
        e.setAttribute("stroke-width", "8");
        e.setAttribute("fill", "none");
        scaleCentered(e, scale, scale);
    });

    mouth.push(function (paper, cx, cy) {
        // Thin smile
        var e, x = cx - 75, y = cy - 15;

        e = newPath(paper);
        e.setAttribute("d", "M " + x + "," + y +
                       "c 0,0 75,60 150,0");
        e.setAttribute("stroke", "#000");
        e.setAttribute("stroke-width", "8");
        e.setAttribute("fill", "none");
    });
    mouth.push(function (paper, cx, cy) {
        // Thin flat
        var e, x = cx - 55, y = cy;

        e = newPath(paper);
        e.setAttribute("d", "M " + x + "," + y +
                       "h 110");
        e.setAttribute("stroke", "#000");
        e.setAttribute("stroke-width", "8");
        e.setAttribute("fill", "none");
    });
    mouth.push(function (paper, cx, cy) {
        // Open-mouthed smile, top teeth
        var e, x = cx - 75, y = cy - 15;

        e = newPath(paper);
        e.setAttribute("d", "M " + x + "," + y +
                       "c 0,0 75,100 150,0" +
                       "h -150");

        e = newPath(paper);
        e.setAttribute("d", "M " + (x + 16) + "," + (y + 8) +
                       "l 16,16" +
                       "h 86" +
                       "l 16,-16" +
                       "h -118");
        e.setAttribute("fill", "#f0f0f0");
    });
    mouth.push(function (paper, cx, cy) {
        // Generic open mouth
        var e, x = cx - 55, y = cy;

        e = newPath(paper);
        e.setAttribute("d", "M " + x + "," + y +
                       "a 54,10 0 1 1 110,0" +
                       "a 54,20 0 1 1 -110,0");
    });
    mouth.push(function (paper, cx, cy) {
        // Thin smile with ends
        var e, x = cx - 75, y = cy - 15;

        e = newPath(paper);
        e.setAttribute("d", "M " + x + "," + y +
                       "c 0,0 75,60 150,0");
        e.setAttribute("stroke", "#000");
        e.setAttribute("stroke-width", "8");
        e.setAttribute("fill", "none");

        e = newPath(paper);
        e.setAttribute("d", "M " + (x + 145) + "," + (y + 19) +
                       "c 15.15229,-18.18274 3.03046,-32.32488 3.03046,-32.32488");
        e.setAttribute("stroke", "#000");
        e.setAttribute("stroke-width", "8");
        e.setAttribute("fill", "none");

        e = newPath(paper);
        e.setAttribute("d", "M " + (x + 5) + "," + (y + 19) +
                       "c -15.15229,-18.18274 -3.03046,-32.32488 -3.03046,-32.32488");
        e.setAttribute("stroke", "#000");
        e.setAttribute("stroke-width", "8");
        e.setAttribute("fill", "none");
    });
    mouth.push(function (paper, cx, cy) {
        // Thin frown (KSW)
        var e, x = cx - 75, y = cy + 15;

        e = newPath(paper);
        e.setAttribute("d", "M " + x + "," + y +
                       "c 0,0 75,-60 150,0");
        e.setAttribute("stroke", "#000");
        e.setAttribute("stroke-width", "8");
        e.setAttribute("fill", "none");
    });

    hair.push(function (paper, fatness, color) {
        // Normal short
        var e;

        e = newPath(paper);
        e.setAttribute("d", "M 200,100" +
                       "c 0,0 180,-10 176,150" +
                       "c 0,0 -180,-150 -352,0" +
                       "c 0,0 0,-160 176,-150");
        e.setAttribute("fill", color);
        scaleCentered(e, fatScale(fatness), 1);
    });
    /*hair.push(function (paper, fatness, color) {
        // Flat top
        var e;

        e = newPath(paper);
        e.setAttribute("d", "M 25,60" +
                       "h 352" +
                       "v 190" +
                       "c 0,0 -180,-150 -352,0" +
                       "v -190");
        e.setAttribute("fill", color);
        scaleCentered(e, fatScale(fatness), 1);
    });*/
    /*hair.push(function (paper, fatness, color) {
        // Afro
        var e;

        e = newPath(paper);
        e.setAttribute("d", "M 25,250" +
                       "a 210,150 0 1 1 352,0" +
                       "c 0,0 -180,-150 -352,0");
        e.setAttribute("fill", color);
        scaleCentered(e, fatScale(fatness), 1);
    });*/
    hair.push(function (paper, fatness, color) {
        // Cornrows
        var e;

        e = newPath(paper);
        e.setAttribute("d", "M 36,229" +
                       "v -10" +
                       "m 40,-10" +
                       "v -60" +
                       "m 50,37" +
                       "v -75" +
                       "m 50,65" +
                       "v -76" +
                       "m 50,76" +
                       "v -76" +
                       "m 50,93" +
                       "v -75" +
                       "m 50,92" +
                       "v -60" +
                       "m 40,80" +
                       "v -10");
        e.setAttribute("stroke", "#000");
        e.setAttribute("stroke-linecap", "round");
        e.setAttribute("stroke-width", "22");
        scaleCentered(e, fatScale(fatness), 1);
    });
    hair.push(function (paper, fatness, color) {
        // Androgenous shortish hair 1  KSW  
        var e;

        e = newPath(paper);
        e.setAttribute("d", "M349.93 287.8c-4.3656-10.3-5.7626-19.4-11.874-27.3-10.652-13.7-23.225-25.3-57.277-23.6-14.843 0.8-30.909-7.6-48.895-12.5v27.8c-22.527-14.7-39.116-25.5-61.293-40-2.4447 12.5-4.191 20.7-5.7626 28.8l-8.382 5.1c-16.065-12.1-31.956-24.1-52.562-39.6-20.082 29-36.671 52.8-56.229 81-10.822-12.1-22.522-21.8-28.284-32.5-19.733-36.8-29.337-71.8 5.763-109.9 21.304-23.2 40.164-35.1 85.571-33.1 9.9536 0.4 21.13-5.2 30.909-8.8 40.338-14.6 115.08-16.9 153.32 9.1 4.8895 3.3 14.145 4.5 21.479 6.5 29.162 7.8 53.086 17.9 54.658 38.6 0.17461 2.4 3.3179 4.8 5.4134 7 35.449 38 24.971 89.7-26.543 123.4z");
        e.setAttribute("fill", color);
        scaleCentered(e, fatScale(fatness), 1);
    });
    hair.push(function (paper, fatness, color) {
        // Androgenous shortish hair 2  KSW  
        var e;

        e = newPath(paper);
        e.setAttribute("d", "M23.683 280.2c-12.181-33.5-25.554-71.95-21.125-104.75 2.7685-20.9 42.081-44.2 76.041-59.1 43.004-19 103.17-14 156.33-17.2 37.098-2.2 115.91 26.4 136.02 43.2 38.66 48.107 32.276 89.836 8.0977 133.54-4.0604 0.1-14.724 4.2136-14.724 4.2136-3.1376-12.9-12.938-30.949-20.874-42.949-7.198-11.2-23.255-20.7-35.437-30.9-2.7685 0.9-5.7215 1.9-8.49 2.8 3.3222 4.7 6.4598 9.4 9.782 14.1-31.561-31.3-96.159-14-141.93-26.9 5.7215 4 11.258 8 18.087 12.9-4.7987 0.2-8.8591 1.1-11.074 0.3-18.826-6.7-36.544-14.2-55.923-20.4-11.074-3.5-28.423-8.6-35.621-6.3-10.336 3.3-18.087 12.4-19.933 19.6-5.9061 24.2-20.115 54.22-24.176 78.52-11.708 2.2068-9.8846-0.87119-15.052-0.67119z");
        e.setAttribute("fill", color);
        scaleCentered(e, fatScale(fatness), 1);
    });
    hair.push(function (paper, fatness, color) {
        // Woman 1  KSW  integers rather than foats
        var e;

        e = newPath(paper);
        e.setAttribute("d", "M5.5 508.8c66.4-22.8 25.6-46.0 8.0-89.2-12.5-122.8-26.5-248.7 46.4-341.6 37.3-26.0 76.6-48.5 132.7-33.9 6.2 1.5 14.5 1.1 20.7-0.7 44.4-13.7 80.1 0.7 113.6 20.3 92.9 80.8 74.0 216.9 63.3 346.8-10.5 11.0-9.9 23.2-14.6 36.3-9.7 27.4-16.3 40.4 32.4 52.1-28.2 40.3-54.5 52.1-96.2 19.3-35.9-28.2-3.2-67.3-17.2-111.2 18.4-36.7 34.8-74.9 42.5-113.6 8.5-43.2 3.5-64.1-53.6-64.6-55.0-0.3-107.8 2.2575-162.9 0.5-31.7-1.1-57.6 23.1-52.8 44.2 9.9 43.8 21.1 87.8 39.6 130.0 16.2 36.6 24.7 70.6-4.5 104.6-33.6 38.6-58.4 40.9-97.4 0.6z");
        e.setAttribute("fill", color);
        scaleCentered(e, fatScale(fatness), 1);
    });
    hair.push(function (paper, fatness, color) {
        // Woman 2  KSW
        var e;

        e = newPath(paper);
        e.setAttribute("d", "M5.5963 508.87c6.0786-68.005 16.13-46.055 8.0714-89.286-12.535-122.85-26.586-248.76 46.405-341.66 37.383-26.029 76.637-48.592 132.71-33.918 6.2306 1.5778 14.538 1.1044 20.769-0.78889 44.445-13.727 80.167 0.78889 113.6 20.353 92.929 80.819 74.084 216.99 63.388 346.89 2.7555 20.578-3.5658 21.379-1.9861 36.382-9.7613 27.453 12.855 39.153 8.9071 62.269-28.245 40.391-43.725 41.995-85.47 9.1765-35.93-28.242-39.253-67.371-17.238-111.23 18.484-36.762 34.891-74.945 42.576-113.6 8.5151-43.231 3.5074-64.192-53.606-64.665-55.037-0.31555-107.87 2.2575-162.9 0.52191-31.776-1.1044-57.637 23.121-52.86 44.263 9.9689 43.862 21.184 87.883 39.668 130.01 16.2 36.605 24.715 70.685-4.5691 104.61-33.645 38.656-83.21 40.277-97.467 0.67968z");
        e.setAttribute("fill", color);
        scaleCentered(e, fatScale(fatness), 1);
    });
    hair.push(function (paper, fatness, color) {
        // Woman 3  KSW
        var e;

        e = newPath(paper);
        e.setAttribute("d", "M5.5963 508.87c6.0786-68.005 16.13-46.055 8.0714-89.286-12.535-122.85-28.493-232.23 44.498-325.13 37.383-26.029 72.188-38.423 128.26-23.749 6.2306 1.5778 32.335 1.74 38.566-0.1533 44.445-13.727 72.54-1.7535 105.97 17.811 92.929 80.819 70.27 192.2 59.574 322.1 2.7555 20.578-3.5658 21.379-1.9861 36.382-9.7613 27.453 12.855 39.153 8.9071 62.269-28.245 40.391-43.725 41.995-85.47 9.1765-35.93-28.242-18.278-65.464 3.7366-109.32 18.484-36.762 13.916-76.852 21.601-115.51 8.5151-43.231 0.96503-89.616-56.148-90.089-55.037-0.31555-102.15 2.2575-157.18 0.52191-31.776-1.1044-60.815 48.545-56.038 69.687 9.9689 43.862 5.2942 92.332 23.778 134.46 16.2 36.605 40.605 66.236 11.321 100.16-33.645 38.656-83.21 40.277-97.467 0.67968z");
        e.setAttribute("fill", color);
        scaleCentered(e, fatScale(fatness), 1);
    });
    hair.push(function (paper, fatness, color) {
        // Woman 4  KSW
        var e;

        e = newPath(paper);
        e.setAttribute("d", "M5.5963 508.87c6.0786-68.005 16.13-46.055 8.0714-89.286-12.535-122.85-28.493-232.23 44.498-325.13 37.383-26.029 72.188-38.423 128.26-23.749 6.2306 1.5778 32.335 1.74 38.566-0.1533 44.445-13.727 72.54-1.7535 105.97 17.811 92.929 80.819 70.27 192.2 59.574 322.1 2.7555 20.578-3.5658 21.379-1.9861 36.382-9.7613 27.453 12.855 39.153 8.9071 62.269-28.245 40.391-27.835-54.616-49.241-99.51 2.2056-45.403-3.0242-8.8964-4.5261-56.569 5.7721-36.762-14.05-20.92-6.3647-59.575 8.5151-43.231 0.96503-89.616-56.148-90.089-55.037-0.31555-102.15 2.2575-157.18 0.52191-31.776-1.1044-60.815 48.545-56.038 69.687-11.006 42.591-29.663 31.951-11.179 74.078 7.9373 40.419 5.6472 17.931-2.0267 62.661-33.645 38.656-34.905 138.16-49.162 98.561z");
        e.setAttribute("fill", color);
        scaleCentered(e, fatScale(fatness), 1);
    });
    hair.push(function (paper, fatness, color) {
        // Woman 5  KSW
        var e;

        e = newPath(paper);
        e.setAttribute("d", "M16.21 317.25c-12.535-122.85-31.035-129.9 41.956-222.8 37.383-26.029 72.188-38.423 128.26-23.749 6.2306 1.5778 32.335 1.74 38.566-0.1533 44.445-13.727 72.54-1.7535 105.97 17.811 92.929 80.819 65.821 115.93 55.125 245.83-12.714 34.31 4.5677 26.201 1.2006 51.618-18.711 40.391-30.377 31.825-33.351 0.91379 6.6522-50.926-23.375-56.214-16.611-93.262 8.5151-43.231 0.96503-89.616-56.148-90.089-55.037-0.31555-102.15 2.2575-157.18 0.52191-31.776-1.1044-60.815 48.545-56.038 69.687-26.753 62.428-12.131 60.058-18.927 98.603-5.0433 45.012-15.202 56.167-29.459 16.57 3.8574-56.608 4.5016-29.772-3.3676-71.497z");
        e.setAttribute("fill", color);
        scaleCentered(e, fatScale(fatness), 1);
    });
    hair.push(function (paper, fatness, color) {
        // Woman 6  KSW
        var e;

        e = newPath(paper);
        e.setAttribute("d", "M16.21 317.25c-12.535-122.85 7.736-145.15 64.202-198.65 37.378-26.027 51.848-27.615 107.92-21.84 36.739 0.30661 39.326 0.46881 45.557-1.4245 49.53-1.0151 69.362-5.567 102.79 13.997 47.166 45.861 60.101 94.956 49.405 224.86-12.714 34.31 4.5677 26.201 1.2006 51.618-18.711 40.391-30.377 31.825-33.351 0.91379 6.6522-50.926-23.375-56.214-16.611-93.262 8.5151-43.231 0.96503-89.616-56.148-90.089-55.037-0.31555-102.15 2.2575-157.18 0.52191-31.776-1.1044-60.815 48.545-56.038 69.687-26.753 62.428-12.131 60.058-18.927 98.603-5.0433 45.012-15.202 56.167-29.459 16.57 3.8574-56.608 4.5016-29.772-3.3676-71.497z");
        e.setAttribute("fill", color);
        scaleCentered(e, fatScale(fatness), 1);
    });
    hair.push(function (paper, fatness, color) {
        // Woman 7  KSW
        var e;

        e = newPath(paper);
        e.setAttribute("d", "M16.21 317.25c-12.535-122.85 7.736-145.15 64.202-198.65 37.378-26.027 51.848-27.615 107.92-21.84 36.739 0.30661 39.326 0.46881 45.557-1.4245 49.53-1.0151 69.362-5.567 102.79 13.997 47.166 45.861 60.101 94.956 49.405 224.86-12.714 34.31 4.5677 26.201 1.2006 51.618-18.711 40.391-30.377 31.825-33.351 0.91379 6.6522-50.926-29.731-102.61-22.967-139.66-13.095-44.502-75.306-59.743-77.759-19.538-2.2828-15.57-95.158-31.429-92.985 6.2422-6.3523-32.884-62.722-26.455-77.013 1.0427-26.753 62.428-27.386 98.829-34.181 137.37-5.0433 45.012-15.202 56.167-29.459 16.57 3.8574-56.608 4.5016-29.772-3.3676-71.497");
        e.setAttribute("fill", color);
        scaleCentered(e, fatScale(fatness), 1);
    });
    hair.push(function (paper, fatness, color) {
        // Woman 8  KSW
        var e;

        e = newPath(paper);
        e.setAttribute("d", "M349.93 287.8c-4.3656-10.3-5.7626-19.4-11.874-27.3-10.652-13.7-23.225-25.3-57.277-23.6-14.843 0.8-30.909-7.6-48.895-12.5v27.8c-22.527-14.7-39.116-25.5-61.293-40-2.4447 12.5-4.191 20.7-5.7626 28.8l-8.382 5.1c-16.06-12.1-31.95-24.1-52.56-39.6-20.078 29-47.472 175.47-67.03 203.67-30.525 8.24-14.259-139.39-20.021-150.09-7.6567-36.16-26.795-76.88 8.305-114.98 21.304-23.2 40.164-35.1 85.571-33.1 9.9536 0.4 21.13-5.2 30.909-8.8 40.338-14.6 115.08-16.9 153.32 9.1 4.8895 3.3 14.145 4.5 21.479 6.5 29.162 7.8 53.086 17.9 54.658 38.6 0.17461 2.4 3.3179 4.8 5.4134 7 35.449 38 21.793 210.46-29.721 244.16z");
        e.setAttribute("fill", color);
        scaleCentered(e, fatScale(fatness), 1);
    });
    hair.push(function (paper, fatness, color) {
        // Woman 9  KSW
        var e;

        e = newPath(paper);
        e.setAttribute("d", "M23.683 280.2c-12.181-33.5-25.554-71.95-21.125-104.75 2.7685-20.9 42.081-44.2 76.041-59.1 43.004-19 103.17-14 156.33-17.2 37.098-2.2 115.91 26.4 136.02 43.2 38.66 48.107 27.191 163.56 3.013 207.27-4.0604 0.1-9.6392-69.515-9.6392-69.515-3.1376-12.9-12.938-30.949-20.874-42.949-7.198-11.2-23.255-20.7-35.437-30.9-2.7685 0.9-5.7215 1.9-8.49 2.8 3.3222 4.7 6.4598 9.4 9.782 14.1-31.561-31.3-96.159-14-141.93-26.9 5.7215 4 30.961 38.508 37.79 43.408-4.7987 0.2-31.74-22.417-30.777-30.208-18.826-6.7-36.544-14.2-55.923-20.4-11.074-3.5-28.423-8.6-35.621-6.3-10.336 3.3-18.087 12.4-19.933 19.6-5.9061 24.2 31.368 188.97-1.9302 184.66-11.708 2.2068-42.3-78.414-37.298-106.82z");
        e.setAttribute("fill", color);
        scaleCentered(e, fatScale(fatness), 1);
    });
    hair.push(function (paper, fatness, color) {
        // Intentionally left blank (bald)
    });

    function getId(array) {
        return Math.floor(Math.random() * array.length);
    }

    /**
     * Display a face.
     * 
     * @param {string} container id of the div that the face will appear in. If not given, no face is drawn and the face object is simply returned.
     * @param {Object} face Face object, such as one generated from faces.generate.
     * @param displaySvg true or false
     * @param returnSvgStr true or false
     */
    function display(container, face, displaySvg, returnSvgStr, text) {
        var paper;
        if (displaySvg && container) {
          container = document.getElementById(container);
          container.innerHTML = "";
        }

        paper = document.createElementNS("http://www.w3.org/2000/svg", "svg");
        paper.setAttribute("version", "1.1"); //"1.2");
        //paper.setAttribute("baseProfile", "tiny");
        // xmlns='http://www.w3.org/2000/svg' xmlns:xlink= 'http://www.w3.org/1999/xlink'
        paper.setAttribute("xmlns", "http://www.w3.org/2000/svg");
        paper.setAttribute("xmlns:xlink", "http://www.w3.org/1999/xlink");
        // none of the following changes make any difference
        paper.setAttribute("width",  "100%"); // "350"
        paper.setAttribute("height", "100%"); // "570"
        paper.setAttribute("viewBox", "0 0 350 570"); // 400 600
        paper.setAttribute("preserveAspectRatio", "xMinYMin meet"); // "xMidYMid meet"
        if (displaySvg && container) {
          container.appendChild(paper);
        }

        head[face.head.id](paper, face.fatness, face.color);
        eyebrow[face.eyebrows[0].id](paper, face.eyebrows[0].lr, face.eyebrows[0].cx, face.eyebrows[0].cy);
        eyebrow[face.eyebrows[1].id](paper, face.eyebrows[1].lr, face.eyebrows[1].cx, face.eyebrows[1].cy);

        eye[face.eyes[0].id](paper, face.eyes[0].lr, face.eyes[0].cx, face.eyes[0].cy, face.eyes[0].angle, face.eyecolor);
        eye[face.eyes[1].id](paper, face.eyes[1].lr, face.eyes[1].cx, face.eyes[1].cy, face.eyes[1].angle, face.eyecolor);

        nose[face.nose.id](paper, face.nose.cx, face.nose.cy, face.nose.size, face.nose.posY, face.nose.flip);
        mouth[face.mouth.id](paper, face.mouth.cx, face.mouth.cy);
        hair[face.hair.id](paper, face.fatness, face.hair.color); // KSW added arg
        
        if (text) {
          // <text style='text-anchor: middle;' x='200' y='520'>Edward</text>
          var e = newText(paper);
          e.setAttribute("class", "facesjs");
          e.setAttribute("x", 200);
          e.setAttribute("y", 535);
          e.setAttribute("style", "text-anchor: middle; font-family: Oswald,Georgia,serif; font-size: 28px;");
          e.textContent = text;
        }
        if (returnSvgStr) {
          return paper.outerHTML;
        }
    }

    /**
     * Generate a random face.
     * 
     * @param {string=} container id of the div that the face will appear in. If not given, no face is drawn and the face object is simply returned.
     * @return {Object} Randomly generated face object.
     */
    function generate(container) {
        var angle, colors, face, flip, id, paper, haircolors, eyecolors;

        face = {head: {}, eyebrows: [{}, {}], eyes: [{}, {}], nose: {}, mouth: {}, hair: {}};
        face.fatness = Math.random();
        colors = ["#f2d6cb", "#ddb7a0", "#ce967d", "#bb876f", "#aa816f", "#a67358", "#ad6453", "#74453d", "#5c3937"];
        face.color = colors[getId(colors)];

        face.head = {id: getId(head)};

        id = getId(eyebrow);
        face.eyebrows[0] = {id: id, lr: "l", cx: 135, cy: 250};
        face.eyebrows[1] = {id: id, lr: "r", cx: 265, cy: 250};

        angle = Math.random() * 50 - 20;
        id = getId(eye);
        face.eyes[0] = {id: id, lr: "l", cx: 135, cy: 280, angle: angle};
        face.eyes[1] = {id: id, lr: "r", cx: 265, cy: 280, angle: angle};
        
        eyecolors = [ // KSW, CSS colors
          "#000","#000","#000", "#000", // Black
          "#92B5F2", // light CornflowerBlue
          "#8B4513","#8B4513","#8B4513", // SaddleBrown
          "#A6A64D" // light Olive
        ];
        face.eyecolor = eyecolors[getId(eyecolors)];

        flip = Math.random() > 0.5 ? true : false;
        face.nose = {id: getId(nose), lr: "l", cx: 200, cy: 330, size: Math.random(), posY: undefined, flip: flip};

        face.mouth = {id: getId(mouth), cx: 200, cy: 400};

        face.hair = {id: getId(hair)};
        
        haircolors = [ // KSW  source: http://jillienedesigns.blogspot.ca/2013/08/rgb-codes.html
          "#090806","#2c222b","#3b3024","#4e433f","#504444","#6a4e42","#554938","#a7856a",
          "#a56b46","#91553d","#533d32","#71635a","#b7a69e","#fff5e1","#cabfb1",
          "#8d4a43","#b55239", // 2 reds
          "#d8c078","#e3cc88","#f2da91","#f2e1ae","#f2e7c7" // 5 blonds
        ];
        face.hair.color = haircolors[getId(haircolors)];

        if (typeof container !== "undefined") {
            display(container, face);
        }

        return face;
    }

    return {
        display: display,
        generate: generate
    };
}());
