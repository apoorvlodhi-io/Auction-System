package com.arango.auction.service.impl;

import com.arango.auction.constants.AuctionExceptions;
import com.arango.auction.constants.BidStatus;
import com.arango.auction.model.Auction;
import com.arango.auction.model.Bid;
import com.arango.auction.model.User;
import com.arango.auction.repository.BidRepository;
import com.arango.auction.repository.UserRepository;
import com.arango.auction.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {
    private JavaMailSender javaMailSender;
    private BidRepository bidRepository;
    private UserRepository userRepository;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    @Override
    public void scheduleEmail(Auction auction) {
        log.info("scheduleEmail called for auctionID:{}, with highest bid:{}",auction.getAuctionId(),auction.getHighestBid());
        Long highestBidAmount = auction.getHighestBid();
        Bid highestBid = bidRepository.findByBidAmountAndBidStatus(highestBidAmount, BidStatus.ACCEPTED);//dont searc
        User winner = userRepository.findById(highestBid.getUserId()).get();
        sendEmail(winner.getEmail(), "You won","You Won text");
        log.info("Email sent to user:{}",winner.getEmail());

    }

//    @Override
//    public void notifyOfNewBid(Auction auction, Bid highestBid) {
//        log.info("notifyOfNewBid called for auctionID:{}, with highest bid:{}",auction.getAuctionId(),auction.getHighestBid());
//        Long highestBidAmount = highestBid.getBidAmount();
//        //problem is to fetch unique users which placed any bid in this auction
//        List<Bid> successfulBids = bidRepository.findBidsByOthers(auction.getAuctionId(),BidStatus.ACCEPTED, highestBid.getUserId());
//        for(Bid bid : successfulBids){
//                Optional<User> optionalUser = userRepository.findById(bid.getUserId());
//                if(optionalUser.isPresent()){
//                    User user = optionalUser.get();
//                    log.info("Email sent to user:{}",user.getEmail());
//                    sendEmail(user.getEmail(), "Notification for new Bid","A new bid has been placed");
//                }else{
//                    throw new AuctionExceptions("User not present");
//                }
//        }
//    }
}
