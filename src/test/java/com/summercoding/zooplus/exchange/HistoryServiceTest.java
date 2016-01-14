package com.summercoding.zooplus.exchange;

import com.summercoding.zooplus.model.Account;
import com.summercoding.zooplus.model.HistoryElement;
import com.summercoding.zooplus.repository.AccountRepository;
import com.summercoding.zooplus.repository.HistoryElementRepository;
import com.summercoding.zooplus.security.AuthenticationNameProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class HistoryServiceTest {
    @Mock
    private AuthenticationNameProvider authenticationNameProvider;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    HistoryElementRepository historyElementRepository;

    @Mock
    private Account account;

    @InjectMocks
    private HistoryService historyService;

    @Before
    public void before() {
        String userName = "userName";
        given(authenticationNameProvider.authenticationName()).willReturn(userName);
        given(accountRepository.findByName(userName)).willReturn(account);
    }

    @Test
    public void Should_StoreHistoryElement() {
        // given
        given(account.getHistory()).willReturn(Collections.emptyList());

        // when
        historyService.storeInHistory("USD", "2015-01-01", new BigDecimal("1.1"));

        // then
        verify(historyElementRepository).save(any(HistoryElement.class));
    }

    @Test
    public void Should_RemoveLastHistoryElement_When_TooManyHistoryElements() {
        // given
        List<HistoryElement> history = createHistoryElements(9);
        HistoryElement last = createHistoryElementWithId(10);
        history.add(last);
        given(account.getHistory()).willReturn(history);

        // when
        historyService.storeInHistory("USD", "2015-01-01", new BigDecimal("1.1"));

        // then
        verify(historyElementRepository).delete(last);
    }

    @Test
    public void Should_StoreHistoryElementWithCurrentDate_When_NoDateGiven() {
        // given
        given(account.getHistory()).willReturn(Collections.emptyList());

        // when
        historyService.storeInHistory("USD", null, new BigDecimal("1.1"));

        // then
        ArgumentCaptor<HistoryElement> captor = ArgumentCaptor.forClass(HistoryElement.class);
        verify(historyElementRepository).save(captor.capture());
        assertThat(captor.getValue().getDate()).isEqualTo("current");
    }

    @Test
    public void Should_RetrieveHistory() {
        // given
        List<HistoryElement> history = createHistoryElements(1);
        given(account.getHistory()).willReturn(history);

        // when
        List<HistoryElement> result = historyService.retrieveHistory();

        // then
        assertThat(result).isEqualTo(history);
    }

    private static List<HistoryElement> createHistoryElements(int n) {
        List<HistoryElement> result = new ArrayList<>();
        for (long i = 0; i < n; i++) {
            HistoryElement element = createHistoryElementWithId(i);
            result.add(element);
        }
        return result;
    }

    private static HistoryElement createHistoryElementWithId(long id) {
        HistoryElement element = new HistoryElement();
        element.setId(id);
        return element;
    }
}