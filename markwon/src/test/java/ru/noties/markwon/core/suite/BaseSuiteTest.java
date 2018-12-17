package ru.noties.markwon.core.suite;

import android.support.annotation.NonNull;
import android.text.Spanned;

import org.robolectric.RuntimeEnvironment;

import ru.noties.markwon.AbstractMarkwonPlugin;
import ru.noties.markwon.Markwon;
import ru.noties.markwon.MarkwonConfiguration;
import ru.noties.markwon.core.CorePlugin;
import ru.noties.markwon.test.TestSpan;
import ru.noties.markwon.test.TestSpanMatcher;
import ru.noties.markwon.test.TestUtil;

abstract class BaseSuiteTest {

  void matches(@NonNull String input, @NonNull TestSpan.Document document) {
    final Spanned spanned = (Spanned) markwon().toMarkdown(input);
    TestSpanMatcher.matches(spanned, document);
  }

  void matchInput(@NonNull String name, @NonNull TestSpan.Document document) {
    matches(read(name), document);
  }

  @NonNull
  String read(@NonNull String name) {
    return TestUtil.read(this, "tests/" + name);
  }

  @NonNull
  Markwon markwon() {
    return Markwon.builder(RuntimeEnvironment.application)
      .use(CorePlugin.create())
      .use(new AbstractMarkwonPlugin() {
        @Override
        public void configureConfiguration(@NonNull MarkwonConfiguration.Builder builder) {
          builder.factory(new TestFactory());
        }
      })
      .build();
  }
}
