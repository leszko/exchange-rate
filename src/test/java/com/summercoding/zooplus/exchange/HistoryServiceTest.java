package com.summercoding.zooplus.exchange;

import com.summercoding.zooplus.model.User;
import com.summercoding.zooplus.model.ExchangeRateRequest;
import com.summercoding.zooplus.repository.UserRepository;
import com.summercoding.zooplus.repository.HistoryElementRepository;
import com.summercoding.zooplus.security.AuthenticationNameProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

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
    private final static int HISTORY_SIZE = 10;

    @Mock
    private AuthenticationNameProvider authenticationNameProvider;

    @Mock
    private UserRepository userRepository;

    @Mock
    HistoryElementRepository historyElementRepository;

    @Mock
    private User user;

    @InjectMocks
    private HistoryService historyService;

    @Before
    public void before() {
        String userName = "userName";
        given(authenticationNameProvider.authenticationName()).willReturn(userName);
        given(userRepository.findByName(userName)).willReturn(user);

        ReflectionTestUtils.setField(historyService, "historySize", HISTORY_SIZE);
    }

    @Test
    public void Should_StoreHistoryElement() {
        // given
        given(user.getHistory()).willReturn(Collections.emptyList());

        // when
        historyService.storeInHistory("USD", "2015-01-01", new BigDecimal("1.1"));

        // then
        verify(historyElementRepository).save(any(ExchangeRateRequest.class));
    }

    @Test
    public void Should_RemoveLastHistoryElement_When_TooManyHistoryElements() {
        // given
        List<ExchangeRateRequest> history = createHistoryElements(9);
        ExchangeRateRequest last = createHistoryElementWithId(10);
        history.add(last);
        given(user.getHistory()).willReturn(history);

        // when
        historyService.storeInHistory("USD", "2015-01-01", new BigDecimal("1.1"));

        // then
        verify(historyElementRepository).delete(last);
    }

    @Test
    public void Should_StoreHistoryElementWithCurrentDate_When_NoDateGiven() {
        // given
        given(user.getHistory()).willReturn(Collections.emptyList());

        // when
        historyService.storeInHistory("USD", null, new BigDecimal("1.1"));

        // then
        ArgumentCaptor<ExchangeRateRequest> captor = ArgumentCaptor.forClass(ExchangeRateRequest.class);
        verify(historyElementRepository).save(captor.capture());
        assertThat(captor.getValue().getDate()).isEqualTo("current");
    }

    @Test
    public void Should_RetrieveHistory() {
        // given
        List<ExchangeRateRequest> history = createHistoryElements(1);
        given(user.getHistory()).willReturn(history);

        // when
        List<ExchangeRateRequest> result = historyService.retrieveHistory();

        // then
        assertThat(result).isEqualTo(history);
    }

    private static List<ExchangeRateRequest> createHistoryElements(int n) {
        List<ExchangeRateRequest> result = new ArrayList<>();
        for (long i = 0; i < n; i++) {
            ExchangeRateRequest element = createHistoryElementWithId(i);
            result.add(element);
        }
        return result;
    }

    private static ExchangeRateRequest createHistoryElementWithId(long id) {
        ExchangeRateRequest element = new ExchangeRateRequest();
        element.setId(id);
        return element;
    }
}