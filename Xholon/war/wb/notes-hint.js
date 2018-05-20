/*
  one source: http://www.vex.net/~trebla/symbols/select.html (public domain)
*/
(function () {
  CodeMirror.notesHint = function(editor) {
    var cur = editor.getCursor();
    var token = editor.getTokenAt(cur);
    token = {start: cur.ch, end: cur.ch, string: "", state: token.state,
                       className: token.string == "." ? "property" : null};
    return {list: getCompletions(),
            from: {line: cur.line, ch: token.start},
            to: {line: cur.line, ch: token.end}};
  }

  function getCompletions() {
    var found = [
'î ĵ a⃗ b⃗ c⃗ d⃗ e⃗ f⃗ g⃗ h⃗ i⃗ j⃗ k⃗ l⃗ m⃗ n⃗ o⃗ p⃗ q⃗ r⃗ s⃗ t⃗ u⃗ v⃗ w⃗ x⃗ y⃗ z⃗',
'≤ ≥ ≠ ≒ × · ÷ √ ± ∓ ∞ ° ∑ ∏ ⌈ ⌉ ⌊ ⌋',
'∧ ∨ ¬ ∀ ∃ ⊤ ⊥',
'⇒ → ⇐ ← ⇔ ↔ ≡ ≅ ≜',
'∈ ⊆ ⊂ ⊇ ⊃ ∩ ∪ ∅ ℕ ℤ ℚ ℝ ℂ',
'∂ ∆ ∇ ⊕ ⊗ ∘ ⟨ ⟩',
'⊑ ⊏ ⊒ ⊐ ⊓ ⊔',
'α β γ δ ε ζ η θ ι κ λ μ ν ξ ο π ρ σ τ υ φ χ ψ ω',
'Α Β Γ Δ Ε Ζ Η Θ Ι Κ Λ Μ Ν Ξ Ο Π Ρ Σ Τ Υ Φ Χ Ψ Ω',
'⁰ ⁱ ² ³ ⁴ ⁵ ⁶ ⁷ ⁸ ⁹',
'₀ ₁ ₂ ₃ ₄ ₅ ₆ ₇ ₈ ₉'
];
    return found;
  }
})();
