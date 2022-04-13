# Things in sight

Not a coherent roadmap. Tangible unsolved or partially solved things.

---

## Unsound features

### Gestures

While dragging, the logic to emulate the element "pressing" against its boundary works properly. Upon release:

**Issue:** Completing the pan gesture snaps to the final `delta` positions without collision checks.
**Goal:** Follow the same transformation rules as during the pan when anchoring the element to a valid final location. The CSS `transform:` property is set to `translate(0,0)` to prepare for the next pan.
**Possibility:** Set `left` and `top` according the element's actual current location. I think this would be reasonable with the values from `.getBoundingClientRect`. I did a 60 second test of this unsuccessfully.
**Possibility:** Introduce the same conditional logic to the final repositioning. The translation would be the same, (0,0). This seems more complex.
