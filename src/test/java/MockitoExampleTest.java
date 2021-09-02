import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MockitoExampleTest {

  @Mock
  private List<String> mockedList;

  @Spy
  private List<String> spiedList = new ArrayList<>();

  @Test
  public void whenNotUseMockAnnotation_thenCorrect() {
    final List<String> mockList = Mockito.mock(List.class);
    mockList.add("one");
    Mockito.verify(mockList).add("one");
    assertEquals(0, mockList.size());

    Mockito.when(mockList.size()).thenReturn(100);
    assertEquals(100, mockList.size());
  }

  @Test
  public void whenUseMockAnnotation_thenMockIsInjected() {
    mockedList.add("one");
    Mockito.verify(mockedList).add("one");
    assertEquals(0, mockedList.size());

    Mockito.when(mockedList.size()).thenReturn(100);
    assertEquals(100, mockedList.size());
  }

  @Test
  public void whenNotUseSpyAnnotation_thenCorrect() {
    final List<String> spyList = Mockito.spy(new ArrayList<String>());
    spyList.add("one");
    spyList.add("two");

    Mockito.verify(spyList).add("one");
    Mockito.verify(spyList).add("two");

    assertEquals(2, spyList.size());

    Mockito.doReturn(100).when(spyList).size();
    assertEquals(100, spyList.size());
  }

  @Test
  public void whenUseSpyAnnotation_thenSpyIsInjectedCorrectly() {
    spiedList.add("one");
    spiedList.add("two");

    Mockito.verify(spiedList).add("one");
    Mockito.verify(spiedList).add("two");

    assertEquals(2, spiedList.size());

    Mockito.doReturn(100).when(spiedList).size();
    assertEquals(100, spiedList.size());
  }

  @Test
  public void whenNotUseCaptorAnnotation_thenCorrect() {
    final List<String> mockList = Mockito.mock(List.class);
    final ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);
    mockList.add("one");
    Mockito.verify(mockList).add(arg.capture());

    assertEquals("one", arg.getValue());
  }

  @Captor
  private
  ArgumentCaptor<String> argCaptor;

  @Test
  public void whenUseCaptorAnnotation_thenTheSam() {
    mockedList.add("one");
    Mockito.verify(mockedList).add(argCaptor.capture());

    assertEquals("one", argCaptor.getValue());
  }

}